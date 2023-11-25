package cn.easii.tutelary.remoting.processor;

import cn.easii.tutelary.InstanceManager;
import cn.easii.tutelary.bean.converter.InstanceConverter;
import cn.easii.tutelary.common.constants.Constants;
import cn.easii.tutelary.message.ClientRegisterRequest;
import cn.easii.tutelary.message.ClientRegisterResponse;
import cn.easii.tutelary.processor.AbstractMessageProcessor;
import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.enums.InstanceStateEnum;
import cn.easii.tutelary.common.utils.NetUtils;
import cn.easii.tutelary.remoting.api.Channel;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientRegisterProcessor extends AbstractMessageProcessor<ClientRegisterRequest> {

    private final InstanceManager instanceManager;
    private final InstanceConverter instanceConverter;

    @Override
    public void process(Channel channel, ClientRegisterRequest clientRegisterRequest) {
        log.info("client : [ {} ] register info : {}", channel, clientRegisterRequest);

        String instanceId = clientRegisterRequest.getInstanceId();

        channel.setAttribute(Constants.ChannelAttributeConstants.INSTANCE_ID, instanceId);

        Instance instanceEntity = Instance.builder()
            .instanceId(instanceId)
            .appName(clientRegisterRequest.getAppName())
            .ip(NetUtils.toAddressString(channel.getRemoteAddress()))
            .registerDate(LocalDateTime.now())
            .state(InstanceStateEnum.VALID)
            .channel(channel)
            .build();

        instanceConverter.jvmInfoToInstance(clientRegisterRequest.getJvmInfo(), instanceEntity);

        instanceManager.registerInstance(instanceEntity);

        // response
        ClientRegisterResponse clientRegisterResponse = new ClientRegisterResponse();
        clientRegisterResponse.setInstanceId(instanceId);
        channel.send(clientRegisterResponse);
    }
}
