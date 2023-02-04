package com.tutelary.processor;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.TypeUtil;
import com.tutelary.common.BaseMessage;
import com.tutelary.common.log.LogFactory;
import com.tutelary.remoting.api.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
