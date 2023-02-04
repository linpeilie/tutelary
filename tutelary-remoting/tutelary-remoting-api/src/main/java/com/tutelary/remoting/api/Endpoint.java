package com.tutelary.remoting.api;

import java.net.InetSocketAddress;

import com.tutelary.remoting.api.exception.RemotingException;

public interface Endpoint {

    void send(Object message) throws RemotingException;

    void close();

    boolean isClosed();

    InetSocketAddress getLocalAddress();

    EndpointContext getEndpointContext();

}
