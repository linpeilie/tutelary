package com.tutelary.remoting.netty;

import com.tutelary.common.RequestMessage;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.Asserts;
import com.tutelary.message.HeartbeatRequest;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.EndpointContext;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.timeout.IdleStateEvent;

@io.netty.channel.ChannelHandler.Sharable
public class NettyClientHandler extends ChannelDuplexHandler {

    private static final Log LOG = LogFactory.get(NettyClientHandler.class);

    private final ChannelHandler handler;

    private final EndpointContext endpointContext;

    public NettyClientHandler(EndpointContext endpointContext, ChannelHandler handler) {
        Asserts.notNull(handler, "handler is null");
        Asserts.notNull(handler, "endpoint context is null");
        this.handler = handler;
        this.endpointContext = endpointContext;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
        try {
            handler.disconnected(channel);
        } finally {
            NettyChannel.removeChannel(ctx.channel());
        }
        LOG.info("The connection of {} -> {} is disconnected.", channel.getLocalAddress(), channel.getRemoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
        handler.received(channel, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
        NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
        boolean isRequest = msg instanceof RequestMessage;
        promise.addListener(future -> {
            if (future.isSuccess()) {
                handler.send(channel, msg);
                return;
            }
            if (future.cause() != null) {
                LOG.warn("Send msg occur error", future.cause());
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
        LOG.debug("channel : {} , caught exception : {}", channel, cause);
        try {
            channel.caught(channel, cause);
        } finally {
            NettyChannel.removeChannelIfDisconected(ctx.channel());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            try {
                NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
                LOG.debug("IdleStateEvent triggered, send heartbeat to channel : {}", channel);
                HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
                channel.send(heartbeatRequest);
            } finally {
                NettyChannel.removeChannelIfDisconected(ctx.channel());
            }
        } else if (WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE.equals(evt)) {
            NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
            handler.connected(channel);
            LOG.info("The connection of {} -> {} is established.", channel.getLocalAddress(),
                channel.getRemoteAddress());
        }
        super.userEventTriggered(ctx, evt);
    }
}
