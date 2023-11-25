package cn.easii.tutelary.remoting;

import cn.easii.tutelary.message.ErrorMessage;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import cn.easii.tutelary.remoting.api.transport.ChannelHandlerAdapter;

public class ClientSendDataHandler extends ChannelHandlerAdapter {

    @Override
    public void connected(Channel channel) throws RemotingException {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("error message");
        channel.send(errorMessage);
    }
}
