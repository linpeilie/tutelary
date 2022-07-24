package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestBaseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Message(cmd = MessageCmd.CLIENT_REGISTER_REQUEST)
public class ClientRegisterRequestMessage extends RequestBaseMessage {

    private String appName;

    private String instanceId;

}
