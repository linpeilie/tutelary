package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.RequestMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.COMMAND_CANCEL_REQUEST)
public class CommandCancelRequest extends RequestMessage {

    private String taskId;

}
