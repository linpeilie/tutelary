package com.tutelary.message;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
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

    @Protobuf(fieldType = FieldType.OBJECT)
    private T data;

}
