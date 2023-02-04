package com.tutelary.remoting.api.transport;

import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.exception.RemotingException;

public class ChannelHandlerAdapter implements ChannelHandler {
    @Override
    public void connected(Channel channel) throws RemotingException {

    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {

    }

    @Override
    public void send(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void caught(Channel channel, Throwable throwable) throws RemotingException {

    }
}
