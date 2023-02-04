package com.tutelary.remoting;

import com.tutelary.message.ErrorMessage;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.exception.RemotingException;
import com.tutelary.remoting.api.transport.ChannelHandlerAdapter;

public class ClientSendDataHandler extends ChannelHandlerAdapter {

    @Override
    public void connected(Channel channel) throws RemotingException {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("error message");
        channel.send(errorMessage);
    }
}
