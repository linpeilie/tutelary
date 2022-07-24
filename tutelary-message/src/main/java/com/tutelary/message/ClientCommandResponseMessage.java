package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseBaseMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.CLIENT_COMMAND_RESPONSE)
public class ClientCommandResponseMessage<T> extends ResponseBaseMessage {

    private String sessionId;

    private String command;

    private String commandType;

    private T data;

}
