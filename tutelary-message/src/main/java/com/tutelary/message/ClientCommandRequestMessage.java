package com.tutelary.message;

import com.baidu.bjf.remoting.protobuf.Any;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestBaseMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.CLIENT_COMMAND_REQUEST)
public class ClientCommandRequestMessage extends RequestBaseMessage {

    private String instanceId;

    private String sessionId;

    private Integer commandType;

    private Integer commandCode;

    @Protobuf(fieldType = FieldType.OBJECT)
    private Any param;

}
