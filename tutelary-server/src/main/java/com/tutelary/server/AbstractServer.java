package com.tutelary.server;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.tutelary.server.properties.ServerEndpointConfig;
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

@Slf4j
public abstract class AbstractServer implements IServer {

    protected final ServerEndpointConfig config;

    private final ChannelInitializer<SocketChannel> channelChannelInitializer;

    protected final EventLoopGroup bossGroup;

    protected final EventLoopGroup workerGroup;

    public AbstractServer(ServerEndpointConfig config,
                          ChannelInitializer<SocketChannel> channelChannelInitializer) {
        this.config = config;
        this.channelChannelInitializer = channelChannelInitializer;

        bossGroup = new NioEventLoopGroup(getBossLoopGroupThreads(), ThreadFactoryBuilder.create().setNamePrefix("tutelary-server-boss").setDaemon(true).build());
        workerGroup = new NioEventLoopGroup(getWorkerLoopGroupThreads(), ThreadFactoryBuilder.create().setNamePrefix("tutelary-server-boss").setDaemon(true).build());
    }

    @Override
    public void start() {
        log.debug("tutelary server endpoint config : {}", config);
        log.info("TutelaryServer[ {} ] start...", this.getClass().getSimpleName());
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(channelChannelInitializer);
            ChannelFuture channelFuture = bootstrap.bind(getPort());
            channelFuture.addListener(future -> {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                }
            });
        } catch (Exception e) {
            log.error("tutelary server error", e);
        }
    }

    @Override
    public void destroy() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("TutelaryServer[ {} ] closed", this.getClass().getSimpleName());
    }

    protected abstract int getBossLoopGroupThreads();

    protected abstract int getWorkerLoopGroupThreads();

    protected abstract int getPort();

}
