package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.ResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Message(cmd = MessageCmd.COMMAND_CANCEL_RESPONSE)
public class CommandCancelResponse extends ResponseMessage {

    private boolean canceled;

    /**
     * Cancel the reason for the failure
     */
    private String failureReason;

    public static CommandCancelResponse cancelSuccess() {
        final CommandCancelResponse commandCancelResponse = new CommandCancelResponse();
        commandCancelResponse.setCanceled(true);
        return commandCancelResponse;
    }

    public static CommandCancelResponse cancelFailure(String failureReason) {
        final CommandCancelResponse commandCancelResponse = new CommandCancelResponse();
        commandCancelResponse.setCanceled(false);
        commandCancelResponse.setFailureReason(failureReason);
        return commandCancelResponse;
    }

}
