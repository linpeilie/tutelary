package cn.easii.tutelary.remoting.netty;

import cn.easii.tutelary.common.constants.TutelaryConstants;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.remoting.netty.codec.ProtobufCodec;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.constants.RemotingResponseCode;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import cn.easii.tutelary.remoting.api.transport.ReconnectClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class NettyClient extends ReconnectClient {

    private static final Log LOG = LogFactory.get(NettyClient.class);

    private static final EventLoopGroup WORK_GROUP =
        new NioEventLoopGroup(new NamedThreadFactory("netty-client-worker-", true));

    private Bootstrap bootstrap;

    private volatile Channel channel;

    public NettyClient(EndpointContext endpointContext, ChannelHandler channelHandler) {
        super(endpointContext, channelHandler, new ProtobufCodec());
    }

    @Override
    protected void doOpen() throws Throwable {
        final NettyClientHandler nettyClientHandler = new NettyClientHandler(getEndpointContext(), this);
        bootstrap = new Bootstrap();
        initBootstrap(nettyClientHandler);
    }

    private void initBootstrap(NettyClientHandler nettyClientHandler) throws URISyntaxException {
        bootstrap.group(WORK_GROUP).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getEndpointContext().getConnectionTimeout())
            .channel(NioSocketChannel.class);

        QueryStringEncoder queryEncoder = new QueryStringEncoder("ws://" + getEndpointContext().getAddress() + "/ws");

        URI uri = queryEncoder.toUri();

        LOG.info("websocket uri : {}", uri);

        WebSocketClientProtocolConfig webSocketClientProtocolConfig =
            WebSocketClientProtocolConfig.newBuilder().webSocketUri(uri).subprotocol(null).allowExtensions(true)
                .version(WebSocketVersion.V13).customHeaders(new DefaultHttpHeaders()).build();

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                    // http请求的解码和编码
                    .addLast(new HttpClientCodec())
                    // 把多个消息转换为一个单一的
                    .addLast(new HttpObjectAggregator(TutelaryConstants.MAX_HTTP_CONTENT_LENGTH))
                    // 处理大数据流
                    .addLast(new ChunkedWriteHandler())
                    // 数据压缩
                    .addLast(new WebSocketServerCompressionHandler())
                    // websocket
                    .addLast(new WebSocketClientProtocolHandler(webSocketClientProtocolConfig))
                    // 加解密
                    .addLast(new NettyCodecHandler(getCodec()))
                    // 心跳
                    .addLast(new IdleStateHandler(3, 0, 0, TimeUnit.SECONDS))
                    // 业务处理
                    .addLast(nettyClientHandler);
            }
        });
    }

    @Override
    protected void doConnect() throws Throwable {
        long start = System.currentTimeMillis();
        ChannelFuture future = bootstrap.connect(getRemoteAddress());
        boolean ret = future.awaitUninterruptibly(getEndpointContext().getConnectionTimeout(), MILLISECONDS);
        if (ret && future.isSuccess()) {
            Channel newChannel = future.channel();
            try {
                Channel oldChannel = NettyClient.this.channel;
                try {
                    if (oldChannel != null) {
                        LOG.info("Close old netty channel {} on create new netty channel {}", oldChannel, newChannel);
                        oldChannel.close();
                    }
                } finally {
                    NettyChannel.removeChannelIfDisconected(oldChannel);
                }
            } finally {
                if (NettyClient.this.isClosed()) {
                    try {
                        LOG.info("Close new netty channel {}, because the client closed.", newChannel);
                        newChannel.close();
                    } finally {
                        NettyClient.this.channel = null;
                        NettyChannel.removeChannelIfDisconected(newChannel);
                    }
                } else {
                    NettyClient.this.channel = newChannel;
                }
            }
        } else if (future.cause() != null) {
            throw new RemotingException(
                future.cause(), RemotingResponseCode.CONNECT_SERVER_UNCAUGHT_EXCEPTION,
                getEndpointContext().getAddress(), getRemoteAddress(), future.cause().getMessage()
            );
        } else {
            throw new RemotingException(
                RemotingResponseCode.CONNECT_SERVER_TIMEOUT,
                getEndpointContext().getAddress(), getRemoteAddress(), getEndpointContext().getConnectionTimeout(),
                (System.currentTimeMillis() - start), NetUtil.getLocalhostStr()
            );
        }
    }

    @Override
    protected void doDisConnect() throws Throwable {
        try {
            NettyChannel.removeChannelIfDisconected(channel);
        } catch (Throwable t) {
            LOG.warn(t.getMessage());
        }
    }

    @Override
    protected void doClose() throws Throwable {
    }

    @Override
    protected cn.easii.tutelary.remoting.api.Channel getChannel() {
        Channel c = channel;
        if (c == null) {
            return null;
        }
        return NettyChannel.getOrAddChannel(getEndpointContext(), c, this);
    }

}
