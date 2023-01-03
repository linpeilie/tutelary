package com.tutelary.message;

import com.baidu.bjf.remoting.protobuf.Any;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Message(cmd = MessageCmd.CLIENT_COMMAND_RESPONSE)
public class ClientCommandResponseMessage extends ResponseMessage {

    private String sessionId;

    private Integer commandType;

    private Integer commandCode;

    private long timestamp;

    @Protobuf(fieldType = FieldType.OBJECT)
    private Any data;

}
