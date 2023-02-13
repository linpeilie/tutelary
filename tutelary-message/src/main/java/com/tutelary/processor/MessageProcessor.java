package com.tutelary.processor;

import com.tutelary.common.BaseMessage;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.remoting.api.Channel;

public interface MessageProcessor<T extends BaseMessage> {

    void process(Channel channel, Object msg);

    default Class<T> getCmdClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), BaseMessage.class);
    }

}
