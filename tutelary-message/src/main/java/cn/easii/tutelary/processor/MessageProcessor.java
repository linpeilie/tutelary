package cn.easii.tutelary.processor;

import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.common.utils.ClassUtil;
import cn.easii.tutelary.remoting.api.Channel;

public interface MessageProcessor<T extends BaseMessage> {

    void process(Channel channel, Object msg);

    default Class<T> getCmdClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), BaseMessage.class);
    }

}
