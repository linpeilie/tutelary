package cn.easii.tutelary.remoting.api;

import cn.easii.tutelary.remoting.api.exception.RemotingException;

public interface ChannelHandler {

    void connected(Channel channel) throws RemotingException;

    void disconnected(Channel channel) throws RemotingException;

    void send(Channel channel, Object message) throws RemotingException;

    void received(Channel channel, Object message) throws RemotingException;

    void caught(Channel channel, Throwable throwable) throws RemotingException;

}
