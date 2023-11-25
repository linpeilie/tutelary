package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.ResponseMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.CLIENT_REGISTER_RESPONSE)
public class ClientRegisterResponse extends ResponseMessage {

    private String instanceId;

}
