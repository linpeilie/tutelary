package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.ResponseMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Message(cmd = MessageCmd.ERROR)
public class ErrorMessage extends ResponseMessage {

    public static ErrorMessage build(String message) {
        final ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatus(Boolean.FALSE);
        errorMessage.setMessage(message);
        return errorMessage;
    }

}
