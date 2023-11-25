package cn.easii.tutelary.handler;

import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.processor.MessageProcessor;
import cn.easii.tutelary.processor.MessageProcessorManager;
import cn.easii.tutelary.common.RequestMessage;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import cn.easii.tutelary.remoting.api.transport.ChannelHandlerAdapter;

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
