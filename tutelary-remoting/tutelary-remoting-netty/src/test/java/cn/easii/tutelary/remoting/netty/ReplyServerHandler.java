package cn.easii.tutelary.remoting.netty;

import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import cn.easii.tutelary.remoting.api.transport.ChannelHandlerAdapter;

public class ReplyServerHandler extends ChannelHandlerAdapter {

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        channel.send(message);
    }

}
