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
import java.util.Arrays;

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

            System.out.println("发送总数据长度 >>>>>>> " + content.readableBytes());
            ByteBuf byteBuf = ctx.alloc().buffer();
            // header
            byteBuf.writeInt(content.readableBytes());
            // body
            byteBuf.writeBytes(content);

            ReferenceCountUtil.release(content);

            while (byteBuf.isReadable()) {
                int chunkSize = Math.min(byteBuf.readableBytes(), CHUNK_SIZE);
                System.out.println("分片数据长度 >>>>>>>>>> " + chunkSize);
                ByteBuf chunk = byteBuf.readSlice(chunkSize).retain();
                ctx.writeAndFlush(new BinaryWebSocketFrame(chunk));
            }
        } else {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame) msg;
            ByteBuf content = frame.content();

            if (content.readableBytes() <= HEADER_SIZE) {
                releaseCumulativeBuffer();
                super.channelRead(ctx, msg);
                return;
            }

            System.out.println("接收到数据包长度 >>>>>>>>>> " + content.readableBytes());

            if (expectedLength == -1) {
                expectedLength = content.readInt();
                System.out.println("期望长度 >>>>>>>>>> " + expectedLength);
                if (expectedLength <= -1) {
                    releaseCumulativeBuffer();
                    super.channelRead(ctx, msg);
                    return;
                }
            }

            cumulativeBuffer.writeBytes(content);
            System.out.println("当前汇总数据包长度 >>>>>>>>>>" + cumulativeBuffer.readableBytes());

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
        releaseCumulativeBuffer();
        super.channelInactive(ctx);
    }

    private void releaseCumulativeBuffer() {
        expectedLength = -1;
        ReferenceCountUtil.release(cumulativeBuffer);
        cumulativeBuffer = Unpooled.buffer();
    }

    private boolean isCompletePacket(ByteBuf cumulativeBuffer) {
        return cumulativeBuffer.readableBytes() == expectedLength;
    }

}
