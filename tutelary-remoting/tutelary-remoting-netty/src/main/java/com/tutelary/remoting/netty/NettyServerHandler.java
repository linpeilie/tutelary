package com.tutelary.remoting.netty;

import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.Asserts;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.netty.utils.ChannelUtils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@io.netty.channel.ChannelHandler.Sharable
public class NettyServerHandler extends ChannelDuplexHandler {

    private static final Log LOG = LogFactory.get(NettyServerHandler.class);

    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    private final ChannelHandler handler;

    private final EndpointContext endpointContext;

    public NettyServerHandler(EndpointContext endpointContext, ChannelHandler handler) {
        Asserts.notNull(handler, "handler is null");
        Asserts.notNull(handler, "endpoint context is null");
        this.handler = handler;
        this.endpointContext = endpointContext;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
        try {
            channels.remove(ChannelUtils.getChannelIP(ctx.channel()));
            handler.disconnected(channel);
        } finally {
            NettyChannel.removeChannel(ctx.channel());
        }
        LOG.info("The connection of {} -> {} is disconnected.", channel.getRemoteAddress(), channel.getLocalAddress());
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
        handler.send(channel, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
        LOG.debug("channel : {} , caught exception : {}", channel, cause);
        try {
            handler.caught(channel, cause);
        } finally {
            NettyChannel.removeChannelIfDisconected(ctx.channel());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // server will close channel when server don't receive any heartbeat from client util timeout.
        if (evt instanceof IdleStateEvent) {
            NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
            try {
                LOG.info("IdleStateEvent triggered, close channel " + channel);
                channel.close();
            } finally {
                NettyChannel.removeChannelIfDisconected(ctx.channel());
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            NettyChannel channel = NettyChannel.getOrAddChannel(endpointContext, ctx.channel(), handler);
            channels.put(ChannelUtils.getChannelIP(ctx.channel()), channel);
            handler.connected(channel);
            LOG.info(
                "The connection of {} -> {} is established", channel.getRemoteAddress(), channel.getLocalAddress());
        }
        super.userEventTriggered(ctx, evt);
    }
}
