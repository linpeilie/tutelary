package cn.easii.tutelary.message.command.param;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandRequest;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.STACK_METHOD)
public class StackRequest extends CommandRequest {

    private String qualifiedClassName;

    private List<String> methodNames;

    private int times = 1;

}
