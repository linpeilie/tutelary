package com.tutelary.client;

import cn.hutool.core.util.NumberUtil;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.Client;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.Transporter;
import com.tutelary.remoting.api.Transporters;
import com.tutelary.remoting.netty.NettyTransporter;
import java.util.List;

public class TutelaryClient {

    private static final Log LOGGER = LogFactory.get(TutelaryClient.class);

    private final Client client;

    public TutelaryClient(List<ChannelHandler> channelHandlers) {

        Transporter transporter = new NettyTransporter();
        Transporters transporters = new Transporters(transporter);

        String tutelaryServerUrl = ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getTutelaryServerUrl();

        String[] arr = tutelaryServerUrl.split(":");

        if (arr.length != 2) {
            LOGGER.error("tutelary server url resolution exception");
            throw new IllegalArgumentException("tutelary server url resolution exception");
        }

        EndpointContext endpointContext = EndpointContext.builder()
            .host(arr[0])
            .port(NumberUtil.parseInt(arr[1]))
            .build();

        client = transporters.connect(endpointContext, channelHandlers.toArray(new ChannelHandler[0]));
    }

    public void destroy() {
        if (client != null) {
            client.close();
        }
    }

    public void reconnect() {
        if (client != null) {
            client.reconnect();
        }
    }

    public void sendData(Object message) {
        if (client != null) {
            client.send(message);
        }
    }

}
