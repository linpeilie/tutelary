package cn.easii.tutelary.remoting;

import cn.easii.tutelary.remoting.netty.PacketSplitHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PacketSplitClient {


    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        URI uri = new URI("ws://localhost:8999/ws");
        EventLoopGroup group = new NioEventLoopGroup();

        WebSocketClientProtocolConfig webSocketClientProtocolConfig =
            WebSocketClientProtocolConfig.newBuilder().webSocketUri(uri).subprotocol(null).allowExtensions(true)
                .version(WebSocketVersion.V13).customHeaders(new DefaultHttpHeaders()).build();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 10));
                        pipeline.addLast(new WebSocketClientProtocolHandler(webSocketClientProtocolConfig));
                        pipeline.addLast(new PacketSplitHandler());
                        pipeline.addLast(new NettyClientHandler());
                    }
                });

            Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    static class NettyClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE.equals(evt)) {
                new ScheduledThreadPoolExecutor(1)
                    .scheduleWithFixedDelay(() -> {
                        System.out.println("ws连接成功");
                        int length = 65537;
                        byte[] bytes = new byte[length];
                        Arrays.fill(bytes, (byte) 127);

                        ByteBuf byteBuf = ctx.alloc().buffer();
                        byteBuf.writeBytes(bytes);

                        ctx.writeAndFlush(byteBuf);
                    }, 2, 30, TimeUnit.SECONDS);
            }
            super.userEventTriggered(ctx, evt);
        }
    }

}
