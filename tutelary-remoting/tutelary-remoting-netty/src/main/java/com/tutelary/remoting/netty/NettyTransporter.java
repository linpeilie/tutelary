package com.tutelary.remoting.netty;

import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.Client;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.RemotingServer;
import com.tutelary.remoting.api.Transporter;
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
