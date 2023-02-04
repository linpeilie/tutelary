package com.tutelary.server.processor;

import com.tutelary.bean.domain.Instance;
import com.tutelary.common.enums.InstanceStateEnum;
import com.tutelary.common.utils.NetUtils;
import com.tutelary.message.ClientRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutelary.InstanceManager;
import com.tutelary.bean.converter.InstanceConverter;
import com.tutelary.message.ClientRegisterRequest;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.remoting.api.Channel;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ClientRegisterProcessor extends AbstractMessageProcessor<ClientRegisterRequest> {

    @Autowired
    private InstanceManager appManager;

    @Autowired
    private InstanceConverter instanceConverter;

    @Override
    public void process(Channel channel, ClientRegisterRequest clientRegisterRequest) {
        log.info("client register info : {}", clientRegisterRequest);

        String instanceId = clientRegisterRequest.getInstanceId();

        Instance instanceEntity = Instance.builder().instanceId(instanceId).appName(clientRegisterRequest.getAppName())
            .ip(NetUtils.toAddressString(channel.getRemoteAddress())).registerDate(LocalDateTime.now())
            .state(InstanceStateEnum.VALID).channel(channel).build();

        instanceConverter.jvmInfoToInstance(clientRegisterRequest.getJvmInfo(), instanceEntity);

        appManager.registerInstance(instanceEntity);

        // response
        ClientRegisterResponse clientRegisterResponse = new ClientRegisterResponse();
        clientRegisterResponse.setInstanceId(instanceEntity.getInstanceId());
        channel.send(clientRegisterResponse);
    }
}
