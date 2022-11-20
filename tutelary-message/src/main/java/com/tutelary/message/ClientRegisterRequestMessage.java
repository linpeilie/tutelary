package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestBaseMessage;
import com.tutelary.common.utils.ParameterUtils;
import com.tutelary.message.command.domain.JvmInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Message(cmd = MessageCmd.CLIENT_REGISTER_REQUEST)
public class ClientRegisterRequestMessage extends RequestBaseMessage {

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
