package com.tutelary.encoder;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.tutelary.annotation.Message;
import com.tutelary.common.BaseMessage;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * @author linpl
 */
public class ProtobufMessageEncoder extends ChannelOutboundHandlerAdapter {

    private static final Log LOGGER = LogFactory.get(ProtobufMessageEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof BaseMessage) {
            ByteBuf byteBuf = encode(ctx, (BaseMessage) msg);
            ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf), promise);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    private ByteBuf encode(ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
        LOGGER.debug("protobuf message encoder : {}", msg);
        Class<BaseMessage> clazz = (Class<BaseMessage>)msg.getClass();
        LOGGER.info("ProtobufMessageEncoder current class loader : {}", Thread.currentThread().getContextClassLoader());
        Codec<BaseMessage> codec = ProtobufProxy.create(clazz);
        byte[] bytes = codec.encode(msg);
        int length = bytes.length;
        ByteBuf byteBuf = ctx.alloc().buffer();
        Message meta = clazz.getAnnotation(Message.class);
        byteBuf.writeByte(meta.cmd());
        byteBuf.writeInt(length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
