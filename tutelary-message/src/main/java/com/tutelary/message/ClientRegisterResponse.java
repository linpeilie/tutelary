package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseMessage;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Message(cmd = MessageCmd.CLIENT_REGISTER_RESPONSE)
public class ClientRegisterResponse extends ResponseMessage {

    private String instanceId;

}
