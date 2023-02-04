package com.tutelary.remoting.api;

import java.net.InetSocketAddress;
import java.util.Collection;

public interface RemotingServer extends Endpoint {

    Collection<Channel> getChannels();

    Channel getChannel(InetSocketAddress remoteAddress);

}
