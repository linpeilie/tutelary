package com.tutelary.processor;

import com.tutelary.common.RequestMessage;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.tutelary.common.BaseMessage;
import com.tutelary.exception.ProtobufDecodeException;
import com.tutelary.remoting.api.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public abstract class AbstractMessageProcessor<T extends BaseMessage> implements MessageProcessor<T> {

    private static final Log LOGGER = LogFactory.get(AbstractMessageProcessor.class);

    @Override
    public void process(Channel channel, Object msg) {
        T message = (T) msg;
        process(channel, message);
    }

    protected abstract void process(Channel channel, T msg);

}
