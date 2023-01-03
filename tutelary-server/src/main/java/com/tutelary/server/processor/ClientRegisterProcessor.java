package com.tutelary.server.processor;

import com.tutelary.bean.converter.InstanceConverter;
import com.tutelary.bean.domain.Instance;
import com.tutelary.common.enums.InstanceStateEnum;
import com.tutelary.common.helper.ChannelHelper;
import com.tutelary.common.processor.ServerMessageProcessor;
import com.tutelary.message.ClientRegisterResponse;
import com.tutelary.InstanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.message.ClientRegisterRequest;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ClientRegisterProcessor extends AbstractMessageProcessor<ClientRegisterRequest>
        implements ServerMessageProcessor<ClientRegisterRequest> {

    @Autowired
    private InstanceManager appManager;

    @Autowired
    private InstanceConverter instanceConverter;

    @Override
    public void process(ChannelHandlerContext ctx, ClientRegisterRequest clientRegisterRequest) {
        log.info("client register info : {}", clientRegisterRequest);

        String instanceId = clientRegisterRequest.getInstanceId();

        Instance instanceEntity = Instance.builder()
                .instanceId(instanceId)
                .appName(clientRegisterRequest.getAppName())
                .ip(ChannelHelper.getChannelIP(ctx.channel()))
                .registerDate(LocalDateTime.now())
                .state(InstanceStateEnum.VALID)
                .channel(ctx.channel())
                .build();

        instanceConverter.jvmInfoToInstance(clientRegisterRequest.getJvmInfo(), instanceEntity);

        appManager.registerInstance(instanceEntity);

        // response
        ClientRegisterResponse clientRegisterResponse = new ClientRegisterResponse();
        clientRegisterResponse.setInstanceId(instanceEntity.getInstanceId());
        ctx.channel().writeAndFlush(clientRegisterResponse);
    }
}
