package cn.easii.tutelary.remoting.netty;

import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.remoting.api.Codec;
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
            ctx.writeAndFlush(byteBuf, promise);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
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
