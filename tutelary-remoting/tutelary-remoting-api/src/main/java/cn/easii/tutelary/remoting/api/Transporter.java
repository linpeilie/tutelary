package cn.easii.tutelary.remoting.api;

import cn.easii.tutelary.remoting.api.exception.RemotingException;

public interface Transporter {

    RemotingServer bind(EndpointContext endpointContext, ChannelHandler handler) throws RemotingException;

    Client connect(EndpointContext endpointContext, ChannelHandler handler) throws RemotingException;

}
