package com.tutelary.remoting.api;

import com.tutelary.remoting.api.exception.RemotingException;

public interface Client extends Endpoint, Channel {

    void reconnect() throws RemotingException;

}
