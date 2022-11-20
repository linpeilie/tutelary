package com.tutelary.message;

import com.baidu.bjf.remoting.protobuf.Any;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestBaseMessage;
import com.tutelary.common.utils.ParameterUtils;
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

    @Override
    public void checkInput() {
        ParameterUtils.notBlank(instanceId, "instance id cannot be blank");
        ParameterUtils.nonNull(commandType, "command type cannot be null");
        ParameterUtils.nonNull(commandCode, "command code cannot be null");
    }
}
