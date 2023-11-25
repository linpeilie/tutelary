package cn.easii.tutelary.remoting;

import cn.easii.tutelary.remoting.properties.ServerEndpointConfig;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.RemotingServer;
import cn.easii.tutelary.remoting.api.Transporter;
import cn.easii.tutelary.remoting.api.Transporters;
import cn.easii.tutelary.remoting.netty.NettyTransporter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TutelaryServer {

    private final EndpointContext endpointContext;
    private final List<ChannelHandler> channelHandlers;
    private final RemotingServer remotingServer;

    public TutelaryServer(ServerEndpointConfig config, List<ChannelHandler> channelHandlers) {
        this.channelHandlers = channelHandlers;
        this.endpointContext = EndpointContext.builder()
            .host(config.getHost())
            .port(config.getPort())
            .build();
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
