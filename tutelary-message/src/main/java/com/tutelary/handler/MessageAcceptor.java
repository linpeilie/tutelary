package com.tutelary.handler;

import com.tutelary.common.BaseMessage;
import com.tutelary.common.RequestMessage;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.exception.RemotingException;
import com.tutelary.remoting.api.transport.ChannelHandlerAdapter;

public class MessageAcceptor extends ChannelHandlerAdapter {

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        if (message instanceof BaseMessage) {
            byte cmd = ((BaseMessage) message).getCmd();
            MessageProcessor<? extends BaseMessage> processor = MessageProcessorManager.getHandler(cmd);
            if (processor == null) {
                return;
            }
            if (message instanceof RequestMessage) {
                ((RequestMessage) message).checkInput();
            }
            processor.process(channel, message);
        }
    }

}
