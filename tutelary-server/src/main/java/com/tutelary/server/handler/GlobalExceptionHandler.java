package com.tutelary.server.handler;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalExceptionHandler extends ChannelDuplexHandler {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause != null) {
            log.error("read data caught exception", cause);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg,  promise.addListener((ChannelFutureListener) channelFuture -> {
            if (!channelFuture.isSuccess()) {
                log.error("write data caught error", channelFuture.cause());
            }
        }));
    }
}
