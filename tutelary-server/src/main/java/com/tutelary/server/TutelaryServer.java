package com.tutelary.server;

import com.tutelary.common.constants.TutelaryConstants;
import com.tutelary.encoder.ProtobufMessageEncoder;
import com.tutelary.event.ChannelEventTrigger;
import com.tutelary.event.ChannelEvents;
import com.tutelary.handler.CmdMessageHandler;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.handler.InstanceConnectionManageHandler;
import com.tutelary.server.properties.ServerEndpointConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TutelaryServer extends AbstractServer {

    public TutelaryServer(ServerEndpointConfig config, MessageProcessorManager messageProcessorManager, ChannelEvents channelEvents) {
        super(config, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG))
                        // HTTP 请求解码编码
                        .addLast(new HttpServerCodec())
                        // 把多个消息转换为一个单一的 FullHttpRequest 或是 FullHttpResponse
                        .addLast(new HttpObjectAggregator(TutelaryConstants.MAX_HTTP_CONTENT_LENGTH))
                        // 处理大数据流
                        .addLast(new ChunkedWriteHandler())
                        // websocket 数据压缩
                        .addLast(new WebSocketServerCompressionHandler())
                        // websocket 处理器
                        .addLast(new WebSocketServerProtocolHandler(config.getPath(), null, true))
                        .addLast(new InstanceConnectionManageHandler())
                        // protobuf encoder
                        .addLast(new ProtobufMessageEncoder())
                        .addLast(new CmdMessageHandler(messageProcessorManager))
                        .addLast(new ChannelEventTrigger(channelEvents))
                        .addLast(new IdleStateHandler(1, 0, 0, TimeUnit.MINUTES));
                        // command handler
            }
        });
    }

    @Override
    protected int getBossLoopGroupThreads() {
        return config.getBossLoopGroupThreads();
    }

    @Override
    protected int getWorkerLoopGroupThreads() {
        return config.getWorkerLoopGroupThreads();
    }

    @Override
    protected int getPort() {
        return config.getPort();
    }
}
