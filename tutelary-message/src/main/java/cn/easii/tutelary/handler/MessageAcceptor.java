package cn.easii.tutelary.handler;

import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.message.ErrorMessage;
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
                try {
                    ((RequestMessage) message).checkInput();
                } catch (BaseException e) {
                    channel.send(ErrorMessage.build(e.getErrorMessage()));
                    return;
                }
            }
            processor.process(channel, message);
        }
    }

}
