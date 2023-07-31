package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.COMMAND_CANCEL_REQUEST)
public class CommandCancelRequest extends RequestMessage {

    private String taskId;

}
