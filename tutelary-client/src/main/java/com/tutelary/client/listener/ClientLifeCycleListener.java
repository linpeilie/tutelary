package com.tutelary.client.listener;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.thread.LoggingUncaughtExceptionHandler;
import com.tutelary.event.AbstractChannelEventListener;
import com.tutelary.message.ClientRegisterRequestMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ClientLifeCycleListener extends AbstractChannelEventListener {

    private final ScheduledExecutorService scheduledExecutorService;

    public ClientLifeCycleListener() {
        scheduledExecutorService =
                new ScheduledThreadPoolExecutor(1, ThreadFactoryBuilder.create().setNamePrefix("client-register-")
                        .setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler()).build());
    }

    private static final Log LOG = LogFactory.get();

    @Override
    public void onHandshakeComplete(ChannelHandlerContext ctx) {
        registerClient(ctx);
    }

    private void registerClient(ChannelHandlerContext ctx) {
        if (!ClientBootstrap.registered) {
            LOG.debug("tutelary connected server, try to register");
            ClientRegisterRequestMessage clientRegisterRequestMessage = new ClientRegisterRequestMessage();
            clientRegisterRequestMessage.setAppName(ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getAppName());
            clientRegisterRequestMessage.setInstanceId(ClientBootstrap.instanceId);
            LOG.info("client register info : {}", clientRegisterRequestMessage);
            ctx.channel().writeAndFlush(clientRegisterRequestMessage);
            scheduledExecutorService.schedule(() -> this.registerClient(ctx), 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onInactive(ChannelHandlerContext ctx) {
        LOG.error("服务端断开连接");
        ClientBootstrap.registered = false;
        ClientBootstrap.connect();
    }

}
