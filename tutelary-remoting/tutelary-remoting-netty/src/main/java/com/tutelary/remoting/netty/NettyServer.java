package com.tutelary.remoting.netty;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.tutelary.common.constants.TutelaryConstants;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.ThrowableUtil;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.RemotingServer;
import com.tutelary.remoting.api.transport.AbstractServer;
import com.tutelary.remoting.netty.codec.ProtobufCodec;
import com.tutelary.common.utils.NetUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyServer extends AbstractServer implements RemotingServer {

    private static final Log LOG = LogFactory.get(NettyServer.class);

    private Map<String, Channel> channels;

    private ServerBootstrap bootstrap;

    private io.netty.channel.Channel channel;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    public NettyServer(EndpointContext endpointContext, ChannelHandler channelHandler) {
        super(endpointContext, channelHandler, new ProtobufCodec());
    }

    @Override
    public Collection<Channel> getChannels() {
        return new ArrayList<>(this.channels.values());
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {
        return channels.get(NetUtils.toAddressString(remoteAddress));
    }

    @Override
    protected void doOpen() throws Throwable {
        bootstrap = new ServerBootstrap();

        bossGroup = new NioEventLoopGroup(1, new NamedThreadFactory("netty-server-boss-", true));
        workerGroup = new NioEventLoopGroup(Math.min(Runtime.getRuntime().availableProcessors() + 1, 32),
            new NamedThreadFactory("netty-server-worker-", true));

        final NettyServerHandler nettyServerHandler = new NettyServerHandler(getEndpointContext(), this);
        channels = nettyServerHandler.getChannels();

        initServerBootstrap(nettyServerHandler);

        ChannelFuture future = bootstrap.bind(getBindAddress());
        future.syncUninterruptibly();
        channel = future.channel();
    }

    private void initServerBootstrap(NettyServerHandler nettyServerHandler) {
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // HTTP 请求解码编码
                                .addLast(new HttpServerCodec())
                                // 把多个消息转换为一个单一的 FullHttpRequest 或是 FullHttpResponse
                                .addLast(new HttpObjectAggregator(TutelaryConstants.MAX_HTTP_CONTENT_LENGTH))
                                // 处理大数据流
                                .addLast(new ChunkedWriteHandler())
                                // websocket 数据压缩
                                .addLast(new WebSocketServerCompressionHandler())
                                // websocket 处理器
                                .addLast(new WebSocketServerProtocolHandler("/ws", null, true))
                                .addLast(new NettyCodecHandler(getCodec()))
                                // 心跳
                                .addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS))
                                .addLast(nettyServerHandler);
                    }
                });
    }

    @Override
    protected void doClose() throws Throwable {
        ThrowableUtil.safeExec(() -> {
            if (channel != null) {
                channel.close();
            }
            Collection<Channel> channels = getChannels();
            CollectionUtil.forEach(channels, (CollUtil.Consumer<Channel>) (value, index) -> ThrowableUtil.safeExec(channel::close));
            ThrowableUtil.safeExec(() -> {
                if (bootstrap != null) {
                    bossGroup.shutdownGracefully().syncUninterruptibly();
                    workerGroup.shutdownGracefully().syncUninterruptibly();
                }
            });
            ThrowableUtil.safeExec(() -> {
                if (channels != null) {
                    channels.clear();
                }
            });
        });
    }
}
