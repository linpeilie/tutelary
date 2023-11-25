package cn.easii.tutelary.processor;

import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.remoting.api.Channel;

public abstract class AbstractMessageProcessor<T extends BaseMessage> implements MessageProcessor<T> {

    private static final Log LOGGER = LogFactory.get(AbstractMessageProcessor.class);

    @Override
    public void process(Channel channel, Object msg) {
        T message = (T) msg;
        process(channel, message);
    }

    protected abstract void process(Channel channel, T msg);

}
