package com.tutelary.event;

import cn.hutool.core.collection.ConcurrentHashSet;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Set;

public class ChannelEvents {

    private final List<? extends ChannelEventListener> LISTENERS;

    public ChannelEvents(List<? extends ChannelEventListener> listeners) {
        this.LISTENERS = listeners;
    }

    public void fireEventActive(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onActive(ctx));
    }

    public void fireEventInActive(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onInactive(ctx));
    }

    public void fireEventHandshakeComplete(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onHandshakeComplete(ctx));
    }

    public void fireEventClientRegistered(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onClientRegistered(ctx));
    }

    public void fireEventExceptionCause(ChannelHandlerContext ctx, Throwable e) {
        LISTENERS.forEach(listener -> listener.onExceptionCaught(ctx, e));
    }

    public void fireEventRead(ChannelHandlerContext ctx, Object obj) {
        LISTENERS.forEach(listener -> listener.onRead(ctx, obj));
    }

    public void fireEventWrite(ChannelHandlerContext ctx, Object msg) {
        LISTENERS.forEach(listener -> listener.onWrite(ctx, msg));
    }

}
