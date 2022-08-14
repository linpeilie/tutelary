package com.tutelary.event;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractChannelEventListener implements ChannelEventListener {

    @Override
    public void onActive(ChannelHandlerContext ctx) {
    }

    @Override
    public void onInactive(ChannelHandlerContext ctx) {
    }

    @Override
    public void onHandshakeComplete(ChannelHandlerContext ctx) {
    }

    @Override
    public void onClientRegistered(ChannelHandlerContext ctx) {
    }

    @Override
    public void onExceptionCaught(ChannelHandlerContext ctx, Throwable e) {
    }

    @Override
    public void onRead(ChannelHandlerContext ctx, Object obj) {
    }

    @Override
    public void onWrite(ChannelHandlerContext ctx, Object msg) {
    }

}
