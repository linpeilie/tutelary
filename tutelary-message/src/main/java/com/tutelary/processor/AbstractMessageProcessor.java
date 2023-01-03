package com.tutelary.processor;

import com.tutelary.common.RequestMessage;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.tutelary.common.BaseMessage;
import com.tutelary.exception.ProtobufDecodeException;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public abstract class AbstractMessageProcessor<T extends BaseMessage> implements MessageProcessor<T> {

    private static final Log LOGGER = LogFactory.get(AbstractMessageProcessor.class);

    @Override
    public void process(ChannelHandlerContext ctx, byte[] bytes) {
        T message;
        try {
            message = this.decode(bytes);
        } catch (IOException e) {
            LOGGER.error("exception occurred at protobuf decode, cmd class : {}", getCmdClass().getName(), e);
            throw new ProtobufDecodeException(e);
        }
        if (message instanceof RequestMessage) {
            ((RequestMessage) message).checkInput();
        }
        process(ctx, message);
    }

    protected abstract void process(ChannelHandlerContext ctx, T message);

    private T decode(byte[] bytes) throws IOException {
        Codec<T> tCodec = ProtobufProxy.create(getCmdClass());
        return tCodec.decode(bytes);
    }

}
