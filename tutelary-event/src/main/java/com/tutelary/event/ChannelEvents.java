package com.tutelary.event;

import cn.hutool.core.collection.ConcurrentHashSet;
import io.netty.channel.ChannelHandlerContext;

import java.util.Set;

public class ChannelEvents {

    private static final Set<ChannelEventListener> LISTENERS = new ConcurrentHashSet<>();

    static void fireEventActive(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onActive(ctx));
    }

    static void fireEventInActive(ChannelHandlerContext ctx) {
        LISTENERS.forEach(listener -> listener.onInactive(ctx));
    }

    static void fireEventExceptionCause(ChannelHandlerContext ctx, Throwable e) {
        LISTENERS.forEach(listener -> listener.onExceptionCaught(ctx, e));
    }

    static void fireEventRead(ChannelHandlerContext ctx, Object obj) {
        LISTENERS.forEach(listener -> listener.onRead(ctx, obj));
    }

    static void fireEventWrite(ChannelHandlerContext ctx, Object msg) {
        LISTENERS.forEach(listener -> listener.onWrite(ctx, msg));
    }

}
