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

    void fireEventActive(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onActive(ctx));
    }

    void fireEventInActive(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onInactive(ctx));
    }

    void fireEventExceptionCause(ChannelHandlerContext ctx, Throwable e) {
        LISTENERS.forEach(listener -> listener.onExceptionCaught(ctx, e));
    }

    void fireEventRead(ChannelHandlerContext ctx, Object obj) {
        LISTENERS.forEach(listener -> listener.onRead(ctx, obj));
    }

    void fireEventWrite(ChannelHandlerContext ctx, Object msg) {
        LISTENERS.forEach(listener -> listener.onWrite(ctx, msg));
    }

}
