package cn.easii.tutelary.client.handler;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.thread.NamedThreadFactory;
import cn.easii.tutelary.message.ClientRegisterRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.ClientBootstrap;
import cn.easii.tutelary.client.InstanceIdHolder;
import cn.easii.tutelary.client.util.MXBeanUtil;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import cn.easii.tutelary.remoting.api.transport.ChannelHandlerAdapter;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@AutoService(ChannelHandler.class)
public class ClientLifeCycleChannelHandler extends ChannelHandlerAdapter {

    private static final Log LOG = LogFactory.get(ClientLifeCycleChannelHandler.class);

    private final ScheduledExecutorService scheduledExecutorService;

    public ClientLifeCycleChannelHandler() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("client-registered"));
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        if (!ClientBootstrap.registered) {
            LOG.debug("tutelary connected server, try to register");
            ClientRegisterRequest clientRegisterRequest = new ClientRegisterRequest();
            clientRegisterRequest.setAppName(ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getAppName());
            clientRegisterRequest.setInstanceId(InstanceIdHolder.getInstanceId());
            clientRegisterRequest.setJvmInfo(MXBeanUtil.getInstance().getJvmInfo());
            LOG.info("client register info : {}", clientRegisterRequest);
            ClientBootstrap.sendData(clientRegisterRequest);
            scheduledExecutorService.schedule(() -> this.connected(channel), 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        LOG.error("服务端断开连接");
        ClientBootstrap.registered = false;
    }
}
