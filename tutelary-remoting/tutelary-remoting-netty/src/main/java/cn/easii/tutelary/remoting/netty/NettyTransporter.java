package cn.easii.tutelary.remoting.netty;

import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.Client;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.RemotingServer;
import cn.easii.tutelary.remoting.api.Transporter;
import cn.easii.tutelary.remoting.api.exception.RemotingException;

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
