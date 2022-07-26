package com.tutelary.server.processor;

import cn.hutool.core.lang.UUID;
import com.tutelary.bean.domain.Instance;
import com.tutelary.common.helper.ChannelHelper;
import com.tutelary.common.processor.ServerMessageProcessor;
import com.tutelary.message.ClientRegisterResponseMessage;
import com.tutelary.InstanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.message.ClientRegisterRequestMessage;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
public class ClientRegisterProcessor extends AbstractMessageProcessor<ClientRegisterRequestMessage>
        implements ServerMessageProcessor<ClientRegisterRequestMessage> {

    @Autowired
    private InstanceManager appManager;

    @Override
    public void process(ChannelHandlerContext ctx, ClientRegisterRequestMessage clientRegisterRequestMessage) {
        log.info("client register info : {}", clientRegisterRequestMessage);

        String instanceId = Optional.ofNullable(clientRegisterRequestMessage.getInstanceId()).orElse(UUID.randomUUID().toString(true));

        Instance instanceEntity = Instance.builder()
                .instanceId(instanceId)
                .appName(clientRegisterRequestMessage.getAppName())
                .ip(ChannelHelper.getChannelIP(ctx.channel()))
                .registerDate(LocalDateTime.now())
                .channel(ctx.channel())
                .build();

        appManager.registerInstance(instanceEntity);

        // response
        ClientRegisterResponseMessage clientRegisterResponseMessage = new ClientRegisterResponseMessage();
        clientRegisterResponseMessage.setInstanceId(instanceEntity.getInstanceId());
        ctx.channel().writeAndFlush(clientRegisterResponseMessage);
    }

    @Override
    public Class<ClientRegisterRequestMessage> getCmdClass() {
        return ClientRegisterRequestMessage.class;
    }
}
