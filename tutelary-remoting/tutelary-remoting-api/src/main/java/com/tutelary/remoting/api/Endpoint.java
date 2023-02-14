package com.tutelary.remoting.api;

import com.tutelary.remoting.api.exception.RemotingException;
import java.net.InetSocketAddress;

public interface Endpoint {

    void send(Object message) throws RemotingException;

    void close();

    boolean isClosed();

    InetSocketAddress getLocalAddress();

    EndpointContext getEndpointContext();

}
