package com.tutelary.processor;

import com.tutelary.common.BaseMessage;
import io.netty.channel.ChannelHandlerContext;

public interface MessageProcessor<T extends BaseMessage> {

    void process(ChannelHandlerContext ctx, byte[] bytes);

    Class<T> getCmdClass();

}
