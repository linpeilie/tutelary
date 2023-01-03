package com.tutelary.server;

import cn.hutool.core.collection.ListUtil;
import com.tutelary.common.constants.TutelaryConstants;
import com.tutelary.encoder.ProtobufMessageEncoder;
import com.tutelary.event.ChannelEventTrigger;
import com.tutelary.event.ChannelEvents;
import com.tutelary.handler.CmdMessageHandler;
import com.tutelary.intf.GrpcRequestAcceptor;
import com.tutelary.intf.process.RequestHandler;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.handler.GlobalExceptionHandler;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TutelaryServer extends AbstractServer {

    public TutelaryServer(ServerEndpointConfig config, List<RequestHandler> requestHandlers) {
        super(config, ListUtil.toList(new GrpcRequestAcceptor()));
    }


    @Override
    protected int getPort() {
        return config.getPort();
    }
}
