package com.tutelary.common.helper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class ChannelHelper {

    public static String getChannelIP(Channel channel) {
        return ((InetSocketAddress) channel.remoteAddress()).getAddress().toString().substring(1);
    }

    public static ChannelFuture closeChannel(Channel channel) {
        return channel.close();
    }

    public static ChannelFuture closeChannel(ChannelHandlerContext ctx) {
        return closeChannel(ctx.channel());
    }

    public static ChannelFuture writeAndFlush(Channel channel, Object msg) {
        return channel.writeAndFlush(msg);
    }

    public static ChannelFuture writeAndFlush(ChannelHandlerContext ctx, Object msg) {
        return writeAndFlush(ctx.channel(), msg);
    }

}
