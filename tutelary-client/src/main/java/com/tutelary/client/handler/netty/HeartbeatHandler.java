package com.tutelary.client.handler.netty;

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
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author linpl
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    private static final Log LOG = LogFactory.get();

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(new PongWebSocketFrame());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
