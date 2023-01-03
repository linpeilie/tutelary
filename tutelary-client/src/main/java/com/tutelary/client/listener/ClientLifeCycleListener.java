package com.tutelary.client.listener;

import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.NamedThreadFactory;
import com.tutelary.client.util.MXBeanUtil;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.event.AbstractChannelEventListener;
import com.tutelary.message.ClientRegisterRequest;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ClientLifeCycleListener extends AbstractChannelEventListener {

    private static final Log LOGGER = LogFactory.get(ClientLifeCycleListener.class);

    private final ScheduledExecutorService scheduledExecutorService;

    private boolean startReport = false;

    public ClientLifeCycleListener() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("client-registered"));
    }


    @Override
    public void onHandshakeComplete(ChannelHandlerContext ctx) {
        registerClient(ctx);
    }

    private void registerClient(ChannelHandlerContext ctx) {
        if (!ClientBootstrap.registered && ctx.channel().isActive()) {
            LOGGER.debug("tutelary connected server, try to register");
            ClientRegisterRequest clientRegisterRequest = new ClientRegisterRequest();
            clientRegisterRequest.setAppName(ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getAppName());
            clientRegisterRequest.setInstanceId(ClientBootstrap.instanceId);
            clientRegisterRequest.setJvmInfo(MXBeanUtil.getInstance().getJvmInfo());
            LOGGER.info("client register info : {}", clientRegisterRequest);
            ctx.channel().writeAndFlush(clientRegisterRequest);
            scheduledExecutorService.schedule(() -> this.registerClient(ctx), 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onInactive(ChannelHandlerContext ctx) {
        LOGGER.error("服务端断开连接");
        ClientBootstrap.registered = false;
        ClientBootstrap.connect();
    }

}
