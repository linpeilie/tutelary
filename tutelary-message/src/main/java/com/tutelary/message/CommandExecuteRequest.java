package com.tutelary.message;

import com.baidu.bjf.remoting.protobuf.Any;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestMessage;
import com.tutelary.common.utils.ParameterUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Message(cmd = MessageCmd.CLIENT_COMMAND_REQUEST)
public class CommandExecuteRequest extends RequestMessage {

    private String taskId;

    private Integer code;

    @Protobuf(fieldType = FieldType.OBJECT)
    private Any param;

    @Override
    public void checkInput() {
        ParameterUtils.notBlank(taskId, "task is cannot be null");
        ParameterUtils.nonNull(code, "command code cannot be null");
    }
}
