package com.tutelary.processor;

import java.io.IOException;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.tutelary.common.BaseMessage;
import com.tutelary.exception.ProtobufDecodeException;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractMessageProcessor<T extends BaseMessage> implements MessageProcessor<T> {

    private static final Log LOG = LogFactory.get();

    @Override
    public void process(ChannelHandlerContext ctx, byte[] bytes) {
        T message;
        try {
            message = this.decode(bytes);
        } catch (IOException e) {
            LOG.error("exception occurred at protobuf decode, cmd class : {}", getCmdClass().getName(), e);
            throw new ProtobufDecodeException(e);
        }
        process(ctx, message);
    }

    protected abstract void process(ChannelHandlerContext ctx, T message);

    private T decode(byte[] bytes) throws IOException {
        Codec<T> tCodec = ProtobufProxy.create(getCmdClass());
        return tCodec.decode(bytes);
    }

}
