package com.tutelary.remoting.netty;

import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.exception.RemotingException;
import com.tutelary.remoting.api.transport.ChannelHandlerAdapter;

public class ReplyServerHandler extends ChannelHandlerAdapter {

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        channel.send(message);
    }

}
