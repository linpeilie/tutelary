package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandRequest;
import com.tutelary.constants.CommandEnum;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.STACK_METHOD)
public class StackRequest extends CommandRequest {

    private String className;

    private List<String> methodNames;

    private int times = 1;

}
