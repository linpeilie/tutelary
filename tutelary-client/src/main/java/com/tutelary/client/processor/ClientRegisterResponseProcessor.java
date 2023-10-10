package com.tutelary.client.processor;

import com.google.auto.service.AutoService;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.ClientRegisterResponse;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.remoting.api.Channel;

@AutoService(MessageProcessor.class)
public class ClientRegisterResponseProcessor extends AbstractMessageProcessor<ClientRegisterResponse> {

    private static final Log LOGGER = LogFactory.get(ClientRegisterResponseProcessor.class);

    @Override
    protected void process(Channel channel, ClientRegisterResponse message) {
        LOGGER.info("client register success : {}", message);
        ClientBootstrap.registered = true;
    }
}
