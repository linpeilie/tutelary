package com.tutelary.remoting.netty;

import com.tutelary.common.BaseMessage;
import com.tutelary.remoting.api.Codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class NettyCodecHandler extends ChannelDuplexHandler {

    private final Codec codec;

    public NettyCodecHandler(Codec codec) {
        this.codec = codec;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof BaseMessage) {
            byte[] bytes = codec.encode(msg);
            ByteBuf byteBuf = ctx.alloc().buffer();
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf), promise);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame)msg;
            ByteBuf byteBuf = frame.content();
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes == 0) {
                super.channelRead(ctx, msg);
                return;
            }
            byte[] bytes = new byte[readableBytes];
            byteBuf.readBytes(bytes);
            Object baseMessage = codec.decode(bytes);
            super.channelRead(ctx, baseMessage);
        } else {
            super.channelRead(ctx, msg);
        }
    }
}
