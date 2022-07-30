package com.tutelary.server;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.StrUtil;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.properties.ServerEndpointConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class TutelaryServer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ServerEndpointConfig config;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initMessageHandler(contextRefreshedEvent);
        this.start();
    }

    private void initMessageHandler(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, MessageProcessor> messageHandlerMap =
            contextRefreshedEvent.getApplicationContext().getBeansOfType(MessageProcessor.class);
        for (MessageProcessor messageHandler : messageHandlerMap.values()) {
            MessageProcessorManager.register(messageHandler);
        }
    }

    private void start() {
        log.debug("tutelary server endpoint config : {}", config);
        EventLoopGroup bossGroup = new NioEventLoopGroup(config.getBossLoopGroupThreads(),
            ThreadFactoryBuilder.create().setNamePrefix("tutelary-server-boss-").setDaemon(true).build());
        EventLoopGroup workerGroup = new NioEventLoopGroup(config.getWorkerLoopGroupThreads(),
            ThreadFactoryBuilder.create().setNamePrefix("tutelary-server-worker-").setDaemon(true).build());

        log.info("TutelaryServer start...");
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new TutelaryServerChannelInitializer(config));
            ChannelFuture channelFuture;
            if (StrUtil.isBlank(config.getHost())) {
                channelFuture = bootstrap.bind(config.getPort());
            } else {
                channelFuture = bootstrap.bind(config.getHost(), config.getPort());
            }
            log.info("tutelary listen at {}:{}", config.getHost(), config.getPort());
            channelFuture.addListener(future -> {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                }
            });
            // TODO
        } catch (Exception e) {
            log.error("tutelary server error", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            log.info("tutelary server websocket closed");
        }

    }

}
