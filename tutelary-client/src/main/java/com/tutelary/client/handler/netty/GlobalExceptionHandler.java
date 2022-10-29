package com.tutelary.client.handler.netty;

import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import io.netty.channel.*;

public class GlobalExceptionHandler extends ChannelDuplexHandler {

    private static final Log LOGGER = LogFactory.get(GlobalExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause != null) {
            LOGGER.error("read data caught exception", cause);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    LOGGER.error("write data caught error", channelFuture.cause());
                }
            }
        }));
    }
}
