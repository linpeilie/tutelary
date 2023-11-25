package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import com.baidu.bjf.remoting.protobuf.Any;
import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.ResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Message(cmd = MessageCmd.CLIENT_COMMAND_RESPONSE)
public class CommandExecuteResponse extends ResponseMessage {

    private String taskId;

    private Integer code;

    private long timestamp;

    @Protobuf(fieldType = FieldType.OBJECT)
    private Any data;

}
