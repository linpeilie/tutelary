package com.tutelary.client.handler;

import com.google.auto.service.AutoService;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.thread.NamedThreadFactory;
import com.tutelary.client.util.MXBeanUtil;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.ClientRegisterRequest;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.exception.RemotingException;
import com.tutelary.remoting.api.transport.ChannelHandlerAdapter;
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
            clientRegisterRequest.setInstanceId(ClientBootstrap.instanceId);
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
