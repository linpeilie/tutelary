package com.tutelary.server.handler;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RelayHandler extends ChannelInboundHandlerAdapter {

    private final Channel targetChannel;

    public RelayHandler(Channel targetChannel) {
        this.targetChannel = targetChannel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (targetChannel.isActive()) {
            targetChannel.writeAndFlush(msg).addListener((ChannelFutureListener)future -> {
                log.info("RelayHandler channelRead writeAndFlush : {}", future.isSuccess());
                if (future.isSuccess()) {
                    ctx.channel().read();
                } else {
                    future.channel().close();
                }
            });
        }
    }
}
