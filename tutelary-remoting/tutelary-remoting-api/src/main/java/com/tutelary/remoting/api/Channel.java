package com.tutelary.remoting.api;

import java.net.InetSocketAddress;

public interface Channel extends Endpoint {

    boolean isConnected();

    InetSocketAddress getRemoteAddress();

}
