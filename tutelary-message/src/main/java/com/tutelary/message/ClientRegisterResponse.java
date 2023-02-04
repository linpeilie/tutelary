package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.CLIENT_REGISTER_RESPONSE)
public class ClientRegisterResponse extends ResponseMessage {

    private String instanceId;

}
