package com.tutelary.client.processor;

import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.ClientRegisterResponseMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import io.netty.channel.ChannelHandlerContext;

public class ClientRegisterResponseProcessor extends AbstractMessageProcessor<ClientRegisterResponseMessage> {

    private static final Log LOGGER = LogFactory.get(ClientRegisterResponseProcessor.class);

    @Override
    protected void process(ChannelHandlerContext ctx, ClientRegisterResponseMessage message) {
        LOGGER.info("client register success : {}", message);
        ClientBootstrap.registered = true;
        ClientBootstrap.channelEvents.fireEventClientRegistered(ctx);
    }

    @Override
    public Class<ClientRegisterResponseMessage> getCmdClass() {
        return ClientRegisterResponseMessage.class;
    }
}
