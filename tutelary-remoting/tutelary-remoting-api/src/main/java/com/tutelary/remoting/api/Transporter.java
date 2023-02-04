package com.tutelary.remoting.api;

import java.net.URL;

import com.tutelary.remoting.api.exception.RemotingException;

public interface Transporter {

    RemotingServer bind(EndpointContext endpointContext, ChannelHandler handler) throws RemotingException;

    Client connect(EndpointContext endpointContext, ChannelHandler handler) throws RemotingException;

}
