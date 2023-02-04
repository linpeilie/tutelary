package com.tutelary.remoting.netty;

import java.net.URL;

import com.tutelary.remoting.api.*;
import com.tutelary.remoting.api.exception.RemotingException;

public class NettyTransporter implements Transporter {
    @Override
    public RemotingServer bind(EndpointContext endpointContext, ChannelHandler handler) throws RemotingException {
        return new NettyServer(endpointContext, handler);
    }

    @Override
    public Client connect(EndpointContext endpointContext, ChannelHandler handler) throws RemotingException {
        return new NettyClient(endpointContext, handler);
    }
}
