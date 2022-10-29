package com.tutelary.client;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.StrUtil;
import com.tutelary.client.handler.netty.GlobalExceptionHandler;
import com.tutelary.client.handler.netty.HeartbeatHandler;
import com.tutelary.common.constants.TutelaryConstants;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.encoder.ProtobufMessageEncoder;
import com.tutelary.event.ChannelEventTrigger;
import com.tutelary.handler.CmdMessageHandler;
import com.tutelary.processor.MessageProcessorManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
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

public class TutelaryClient {

    private static final Log LOGGER = LogFactory.get(TutelaryClient.class);

    private Bootstrap bootstrap;

    private static final EventLoopGroup WORK_GROUP =
            new NioEventLoopGroup(ThreadFactoryBuilder.create().setNamePrefix("tutelary-client-worker-").build());

    private final MessageProcessorManager messageProcessorManager;

    public TutelaryClient(MessageProcessorManager messageProcessorManager) {
        this.messageProcessorManager = messageProcessorManager;
        try {
            this.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void start() throws URISyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String tutelaryServerUrl = ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getTutelaryServerUrl();
        LOGGER.info("tutelary service url : {}", tutelaryServerUrl);
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

//        Class<?> protobufMessageEncoderClass = ClassLoaderWrapper.getAgentClassLoader().loadClass("com.tutelary.encoder.ProtobufMessageEncoder");
//        ProtobufMessageEncoder protobufMessageEncoder = (ProtobufMessageEncoder) protobufMessageEncoderClass.newInstance();

        bootstrap = new Bootstrap();
        bootstrap.group(WORK_GROUP)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getConnectTimeout())
                .remoteAddress(host, port)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
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
                                // protobuf encoder
                                .addLast(new ProtobufMessageEncoder())
                                .addLast(new ChannelEventTrigger(ClientBootstrap.channelEvents))
                                // 业务处理
                                .addLast(new CmdMessageHandler(messageProcessorManager))
                                // 心跳
                                .addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS))
                                .addLast(new HeartbeatHandler())
                                .addLast(new GlobalExceptionHandler());
                    }
                });
    }

    public void connect() {
        ChannelFuture cf = bootstrap.connect();
        cf.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                LOGGER.info("连接 tutelary server 成功");
            } else {
                cf.channel().eventLoop().schedule(this::connect, 30, TimeUnit.SECONDS);
            }
        });
    }

    public void destroy() {
        WORK_GROUP.shutdownGracefully();
    }

}
