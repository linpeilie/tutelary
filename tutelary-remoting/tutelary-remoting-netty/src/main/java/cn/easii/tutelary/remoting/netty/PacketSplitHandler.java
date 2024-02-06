package cn.easii.tutelary.remoting.netty;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.ReferenceCountUtil;

public class PacketSplitHandler extends ChannelDuplexHandler {

    private static final Log LOG = LogFactory.get(PacketSplitHandler.class);

    private static final int CHUNK_SIZE = 65536;

    private static final int HEADER_SIZE = 4;

    private ByteBuf cumulativeBuffer = Unpooled.buffer();

    private int expectedLength = -1;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf content = (ByteBuf) msg;

            ByteBuf byteBuf = ctx.alloc().buffer();
            // header
            byteBuf.writeInt(content.readableBytes());
            // body
            byteBuf.writeBytes(content);

            ReferenceCountUtil.release(content);

            while (byteBuf.isReadable()) {
                int chunkSize = Math.min(byteBuf.readableBytes(), CHUNK_SIZE);
                ByteBuf chunk = byteBuf.readSlice(chunkSize).retain();
                ctx.writeAndFlush(new BinaryWebSocketFrame(chunk));
            }

            ReferenceCountUtil.release(byteBuf);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame) msg;
            ByteBuf content = frame.content();

            if (expectedLength == -1 && content.readableBytes() > HEADER_SIZE) {
                expectedLength = content.readInt();
                if (expectedLength <= -1) {
                    releaseCumulativeBuffer();
                    super.channelRead(ctx, msg);
                    return;
                }
            }

            cumulativeBuffer.writeBytes(content);

            if (isCompletePacket(cumulativeBuffer)) {
                ByteBuf buffer = ctx.alloc().buffer();
                buffer.writeBytes(cumulativeBuffer);
                releaseCumulativeBuffer();
                super.channelRead(ctx, buffer);
            } else if (cumulativeBuffer.readableBytes() > expectedLength) {
                LOG.error("data package length error, exceeding expected length");
                releaseCumulativeBuffer();
                super.channelRead(ctx, msg);
            } else {
                super.channelRead(ctx, msg);
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            ReferenceCountUtil.release(cumulativeBuffer);
        } catch (Throwable e) {
            // ignore
        }
        super.channelInactive(ctx);
    }

    private void releaseCumulativeBuffer() {
        try {
            expectedLength = -1;
            ReferenceCountUtil.release(cumulativeBuffer);
            cumulativeBuffer = Unpooled.buffer();
        } catch (Throwable e) {
            // ignore
        }
    }

    private boolean isCompletePacket(ByteBuf cumulativeBuffer) {
        return cumulativeBuffer.readableBytes() == expectedLength;
    }

}
