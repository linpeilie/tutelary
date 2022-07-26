package com.tutelary.message;

import com.baidu.bjf.remoting.protobuf.Any;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseBaseMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.CLIENT_COMMAND_RESPONSE)
public class ClientCommandResponseMessage extends ResponseBaseMessage {

    private String sessionId;

    private String command;

    private Integer commandType;

    private Integer commandCode;

    @Protobuf(fieldType = FieldType.OBJECT)
    private Any data;

}
