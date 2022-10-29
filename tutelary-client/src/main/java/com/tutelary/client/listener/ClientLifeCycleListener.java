package com.tutelary.client.listener;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.thread.LoggingUncaughtExceptionHandler;
import com.tutelary.event.AbstractChannelEventListener;
import com.tutelary.message.ClientRegisterRequestMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ClientLifeCycleListener extends AbstractChannelEventListener {

    private static final Log LOGGER = LogFactory.get(ClientLifeCycleListener.class);

    private final ScheduledExecutorService scheduledExecutorService;

    public ClientLifeCycleListener() {
        scheduledExecutorService =
                new ScheduledThreadPoolExecutor(1, ThreadFactoryBuilder.create().setNamePrefix("client-register-")
                        .setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler()).build());
    }


    @Override
    public void onHandshakeComplete(ChannelHandlerContext ctx) {
        registerClient(ctx);
    }

    private void registerClient(ChannelHandlerContext ctx) {
        if (!ClientBootstrap.registered) {
            LOGGER.debug("tutelary connected server, try to register");
            ClientRegisterRequestMessage clientRegisterRequestMessage = new ClientRegisterRequestMessage();
            clientRegisterRequestMessage.setAppName(ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getAppName());
            clientRegisterRequestMessage.setInstanceId(ClientBootstrap.instanceId);
            LOGGER.info("client register info : {}", clientRegisterRequestMessage);
            ctx.channel().writeAndFlush(clientRegisterRequestMessage);
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
