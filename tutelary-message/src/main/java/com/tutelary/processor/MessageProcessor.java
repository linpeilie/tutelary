package com.tutelary.processor;

import java.lang.reflect.Type;

import com.tutelary.common.BaseMessage;
import com.tutelary.remoting.api.Channel;

import cn.hutool.core.util.TypeUtil;

public interface MessageProcessor<T extends BaseMessage> {

    void process(Channel channel, Object msg);

    default Class<T> getCmdClass() {
        Type[] typeArguments = TypeUtil.getTypeArguments(getClass());
        for (Type typeArgument : typeArguments) {
            if (BaseMessage.class.isAssignableFrom(TypeUtil.getClass(typeArgument))) {
                return (Class<T>)TypeUtil.getClass(typeArgument);
            }
        }
        throw new RuntimeException("unknown message class");
    }

}
