package com.tutelary.server;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.tutelary.server.properties.ServerEndpointConfig;
import io.grpc.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class AbstractServer implements IServer {

    protected final ServerEndpointConfig config;

    private final Server server;


    public AbstractServer(ServerEndpointConfig config,
                          List<BindableService> bindableServices) {
        this.config = config;

        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(getPort());
        bindableServices.forEach(serverBuilder::addService);

        this.server = serverBuilder.build();
    }

    @Override
    public void start() {
        log.debug("tutelary server endpoint config : {}", config);
        log.info("TutelaryServer[ {} ] start...", this.getClass().getSimpleName());
        try {
            server.start();
            log.info("TutelaryServer [ {} ] started, listening on {}", this.getClass().getSimpleName(), getPort());
        } catch (Exception e) {
            log.error("tutelary server error", e);
        }
    }

    @Override
    public void destroy() {
        log.info("TutelaryServer[ {} ] closed", this.getClass().getSimpleName());
    }

    protected abstract int getPort();

}
