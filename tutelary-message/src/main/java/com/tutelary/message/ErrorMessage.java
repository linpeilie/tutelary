package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Message(cmd = MessageCmd.ERROR)
public class ErrorMessage extends ResponseMessage {

    private String lastCmd;

}
