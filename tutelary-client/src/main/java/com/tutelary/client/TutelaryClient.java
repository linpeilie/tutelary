package com.tutelary.client;

import java.net.URI;
import java.net.URISyntaxException;

import com.tutelary.client.handler.netty.HeartbeatHandler;
import com.tutelary.common.constants.TutelaryConstants;
import com.tutelary.encoder.ProtobufMessageEncoder;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.handler.CmdMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class TutelaryClient {

    private static final Log LOG = LogFactory.get();

    private static final EventLoopGroup WORK_GROUP =
        new NioEventLoopGroup(ThreadFactoryBuilder.create().setNamePrefix("tutelary-client-worker-").build());

    public Channel start() throws URISyntaxException, InterruptedException {
        String tutelaryServerUrl = ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getTutelaryServerUrl();
        LOG.info("tutelary service url : {}", tutelaryServerUrl);
        QueryStringEncoder queryEncoder = new QueryStringEncoder(tutelaryServerUrl);
        URI uri = queryEncoder.toUri();
        String host = uri.getHost();
        int port = uri.getPort();
        String scheme = uri.getScheme();

        Assert.notBlank(host);
        Assert.notBlank(scheme);
        Assert.isTrue(StrUtil.equalsAny(scheme, "ws", "wss"), "only ws(s) is supported.");

        WebSocketClientProtocolConfig webSocketClientProtocolConfig =
            WebSocketClientProtocolConfig.newBuilder().webSocketUri(uri).subprotocol(null).allowExtensions(true)
                .version(WebSocketVersion.V13).customHeaders(new DefaultHttpHeaders()).build();
        WebSocketClientProtocolHandler webSocketClientProtocolHandler =
            new WebSocketClientProtocolHandler(webSocketClientProtocolConfig);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(WORK_GROUP)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getConnectTimeout())
            .channel(NioSocketChannel.class).remoteAddress(host, port).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                        // http请求的解码和编码
                        .addLast(new HttpClientCodec())
                        // 把多个消息转换为一个单一的 FullHttpRequest 或 FullHttpResponse
                        .addLast(new HttpObjectAggregator(TutelaryConstants.MAX_HTTP_CONTENT_LENGTH))
                        // 处理大数据流
                        .addLast(new ChunkedWriteHandler())
                        // 数据压缩
                        .addLast(new WebSocketServerCompressionHandler())
                        // websocket
                        .addLast(webSocketClientProtocolHandler)
                        // protobuf encoder
                        .addLast(new ProtobufMessageEncoder())
                        // 注册服务
                        .addLast(new HeartbeatHandler())
                        // 业务处理
                        .addLast(new CmdMessageHandler());
                }
            });

        return bootstrap.connect().sync().channel();
    }

}
