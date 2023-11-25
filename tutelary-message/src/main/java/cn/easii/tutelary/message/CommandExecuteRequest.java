package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import com.baidu.bjf.remoting.protobuf.Any;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.RequestMessage;
import cn.easii.tutelary.common.utils.ParameterUtils;
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
