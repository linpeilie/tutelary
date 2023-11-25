package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.message.command.domain.JvmInfo;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.RequestMessage;
import cn.easii.tutelary.common.utils.ParameterUtils;
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
