package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestMessage;
import com.tutelary.common.utils.ParameterUtils;
import com.tutelary.message.command.domain.JvmInfo;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.CLIENT_REGISTER_REQUEST)
public class ClientRegisterRequest extends RequestMessage {

    private String appName;

    private String instanceId;

    private JvmInfo jvmInfo;

    @Override
    public void checkInput() {
        ParameterUtils.notBlank(appName, "app name cannot be blank");
        ParameterUtils.notBlank(instanceId, "instance id cannot be blank");
        ParameterUtils.nonNull(jvmInfo, "jvm info cannot be null");
    }
}
