package com.tutelary.client.processor;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.message.ClientRegisterResponseMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import io.netty.channel.ChannelHandlerContext;

public class ClientRegisterResponseProcessor extends AbstractMessageProcessor<ClientRegisterResponseMessage> {

    private static final Log LOG = LogFactory.get();

    @Override
    protected void process(ChannelHandlerContext ctx, ClientRegisterResponseMessage message) {
        LOG.info("client register success : {}", message);
        ClientBootstrap.registered = true;
        ClientBootstrap.channelEvents.fireEventClientRegistered(ctx);
    }

    @Override
    public Class<ClientRegisterResponseMessage> getCmdClass() {
        return ClientRegisterResponseMessage.class;
    }
}
