package cn.easii.tutelary.client.processor;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.message.ClientRegisterResponse;
import cn.easii.tutelary.processor.AbstractMessageProcessor;
import cn.easii.tutelary.processor.MessageProcessor;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.ClientBootstrap;
import cn.easii.tutelary.remoting.api.Channel;

@AutoService(MessageProcessor.class)
public class ClientRegisterResponseProcessor extends AbstractMessageProcessor<ClientRegisterResponse> {

    private static final Log LOGGER = LogFactory.get(ClientRegisterResponseProcessor.class);

    @Override
    protected void process(Channel channel, ClientRegisterResponse message) {
        LOGGER.info("client register success : {}", message);
        ClientBootstrap.registered = true;
    }
}
