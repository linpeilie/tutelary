package com.tutelary.processor;

import com.tutelary.common.BaseMessage;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.remoting.api.Channel;

public abstract class AbstractMessageProcessor<T extends BaseMessage> implements MessageProcessor<T> {

    private static final Log LOGGER = LogFactory.get(AbstractMessageProcessor.class);

    @Override
    public void process(Channel channel, Object msg) {
        T message = (T) msg;
        process(channel, message);
    }

    protected abstract void process(Channel channel, T msg);

}
