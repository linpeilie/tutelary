package cn.easii.tutelary.processor;

import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.remoting.api.Channel;
import cn.hutool.core.exceptions.ExceptionUtil;

public abstract class AbstractMessageProcessor<T extends BaseMessage> implements MessageProcessor<T> {

    private static final Log LOGGER = LogFactory.get(AbstractMessageProcessor.class);

    @Override
    public void process(Channel channel, Object msg) {
        T message = (T) msg;
        try {
            process(channel, message);
        } catch (Exception e) {
            LOGGER.error("Message Processor : {} process occur error, cause : {}", this.getClass().getSimpleName(),
                ExceptionUtil.stacktraceToString(e));
        }
    }

    protected abstract void process(Channel channel, T msg);

}
