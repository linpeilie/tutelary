package com.tutelary.server.processor;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.tutelary.bean.converter.InstanceConverter;
import com.tutelary.bean.domain.Instance;
import com.tutelary.common.enums.InstanceStateEnum;
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

    @Autowired
    private InstanceConverter instanceConverter;

    @Override
    public void process(ChannelHandlerContext ctx, ClientRegisterRequestMessage clientRegisterRequestMessage) {
        log.info("client register info : {}", clientRegisterRequestMessage);

        String instanceId = clientRegisterRequestMessage.getInstanceId();

        Instance instanceEntity = Instance.builder()
                .instanceId(instanceId)
                .appName(clientRegisterRequestMessage.getAppName())
                .ip(ChannelHelper.getChannelIP(ctx.channel()))
                .registerDate(LocalDateTime.now())
                .state(InstanceStateEnum.VALID)
                .channel(ctx.channel())
                .build();

        instanceConverter.jvmInfoToInstance(clientRegisterRequestMessage.getJvmInfo(), instanceEntity);

        appManager.registerInstance(instanceEntity);

        // response
        ClientRegisterResponseMessage clientRegisterResponseMessage = new ClientRegisterResponseMessage();
        clientRegisterResponseMessage.setInstanceId(instanceEntity.getInstanceId());
        ctx.channel().writeAndFlush(clientRegisterResponseMessage);
    }
}
