package cn.easii.tutelary.remoting.api;

import cn.easii.tutelary.remoting.api.exception.RemotingException;

public interface Client extends Endpoint, Channel {

    void reconnect() throws RemotingException;

}
