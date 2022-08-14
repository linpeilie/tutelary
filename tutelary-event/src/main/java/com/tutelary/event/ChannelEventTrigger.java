package com.tutelary.event;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class ChannelEventTrigger extends ChannelDuplexHandler {

    private final ChannelEvents channelEvents;

    public ChannelEventTrigger(ChannelEvents channelEvents) {
        this.channelEvents = channelEvents;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelEvents.fireEventActive(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            channelEvents.fireEventHandshakeComplete(ctx);
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelEvents.fireEventInActive(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        channelEvents.fireEventExceptionCause(ctx, cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channelEvents.fireEventRead(ctx, msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        channelEvents.fireEventWrite(ctx, msg);
        super.write(ctx, msg, promise);
    }
}
