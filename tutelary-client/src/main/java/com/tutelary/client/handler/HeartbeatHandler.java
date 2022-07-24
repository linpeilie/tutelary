package com.tutelary.client.handler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.thread.LoggingUncaughtExceptionHandler;
import com.tutelary.message.ClientRegisterRequestMessage;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;

/**
 * @author linpl
 */
@ChannelHandler.Sharable
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    private static final Log LOG = LogFactory.get();

    private Channel channel;

    private final ScheduledExecutorService scheduledExecutorService;

    public HeartbeatHandler() {
        scheduledExecutorService =
            new ScheduledThreadPoolExecutor(1, ThreadFactoryBuilder.create().setNamePrefix("client-register-")
                .setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler()).build());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE.equals(evt)) {
            channel = ctx.channel();
            start();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientBootstrap.registered = false;
        super.channelInactive(ctx);
    }

    private void start() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (!ClientBootstrap.registered) {
                ClientRegisterRequestMessage clientRegisterRequestMessage = new ClientRegisterRequestMessage();
                clientRegisterRequestMessage.setAppName(ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getAppName());
                clientRegisterRequestMessage.setInstanceId(ClientBootstrap.instanceId);
                LOG.info("client register info : {}", clientRegisterRequestMessage);
                channel.writeAndFlush(clientRegisterRequestMessage);
            } else {
                channel.writeAndFlush(new PongWebSocketFrame());
            }
        }, 5, 10, TimeUnit.SECONDS);
    }

}
