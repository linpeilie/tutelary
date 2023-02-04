package com.tutelary.server;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.tutelary.remoting.api.*;
import com.tutelary.remoting.netty.NettyTransporter;
import com.tutelary.server.properties.ServerEndpointConfig;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TutelaryServer {

    private final ServerEndpointConfig config;

    private RemotingServer remotingServer;

    private final EndpointContext endpointContext;

    private final List<ChannelHandler> channelHandlers;

    public TutelaryServer(ServerEndpointConfig config, List<ChannelHandler> channelHandlers) {
        this.config = config;
        this.channelHandlers = channelHandlers;
        this.endpointContext = EndpointContext.builder()
                .host(config.getHost())
                .port(config.getPort())
                .build();
    }

    public void start() {
        log.debug("tutelary server endpoint config : {}", config);
        log.info("TutelaryServer [ {} ] start...", this.getClass().getSimpleName());
        Transporter transporter = new NettyTransporter();
        Transporters transporters = new Transporters(transporter);
        remotingServer = transporters.bind(endpointContext, this.channelHandlers.toArray(new ChannelHandler[0]));
    }

    public void destroy() {
        remotingServer.close();
    }

}
