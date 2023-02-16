package com.tutelary.server;

import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.RemotingServer;
import com.tutelary.remoting.api.Transporter;
import com.tutelary.remoting.api.Transporters;
import com.tutelary.remoting.netty.NettyTransporter;
import com.tutelary.server.properties.ServerEndpointConfig;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TutelaryServer {

    private final ServerEndpointConfig config;
    private final EndpointContext endpointContext;
    private final List<ChannelHandler> channelHandlers;
    private RemotingServer remotingServer;

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
