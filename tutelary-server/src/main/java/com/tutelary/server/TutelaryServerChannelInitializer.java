package com.tutelary.server;

import com.tutelary.common.constants.TutelaryConstants;
import com.tutelary.encoder.ProtobufMessageEncoder;
import com.tutelary.handler.CmdMessageHandler;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.handler.InstanceConnectionManageHandler;
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

import java.util.concurrent.TimeUnit;

public class TutelaryServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final String wsUrl;
    private final MessageProcessorManager messageProcessorManager;

    public TutelaryServerChannelInitializer(String wsUrl, MessageProcessorManager messageProcessorManager) {
        this.wsUrl = wsUrl;
        this.messageProcessorManager = messageProcessorManager;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG))
                // HTTP 请求解码编码
                .addLast(new HttpServerCodec())
                // 把多个消息转换为一个单一的 FullHttpRequest 或是 FullHttpResponse
                .addLast(new HttpObjectAggregator(TutelaryConstants.MAX_HTTP_CONTENT_LENGTH))
                // 处理大数据流
                .addLast(new ChunkedWriteHandler())
                // websocket 数据压缩
                .addLast(new WebSocketServerCompressionHandler())
                // websocket 处理器
                .addLast(new WebSocketServerProtocolHandler(wsUrl, null, true))
                .addLast(new IdleStateHandler(0, 0, 5, TimeUnit.MINUTES))
                .addLast(new InstanceConnectionManageHandler())
                // protobuf encoder
                .addLast(new ProtobufMessageEncoder())
                // command handler
                .addLast(new CmdMessageHandler(messageProcessorManager));
    }

}
