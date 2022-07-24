package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseBaseMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.CLIENT_REGISTER_RESPONSE)
public class ClientRegisterResponseMessage extends ResponseBaseMessage {

    private String instanceId;

}
