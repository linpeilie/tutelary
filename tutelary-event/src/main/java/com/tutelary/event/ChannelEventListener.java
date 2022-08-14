package com.tutelary.event;

import io.netty.channel.ChannelHandlerContext;

public interface ChannelEventListener {

    void onActive(ChannelHandlerContext ctx);

    void onInactive(ChannelHandlerContext ctx);

    /**
     * 握手完成
     */
    void onHandshakeComplete(ChannelHandlerContext ctx);

    void onClientRegistered(ChannelHandlerContext ctx);

    void onExceptionCaught(ChannelHandlerContext ctx, Throwable e);

    void onRead(ChannelHandlerContext ctx, Object obj);

    void onWrite(ChannelHandlerContext ctx, Object msg);

}
