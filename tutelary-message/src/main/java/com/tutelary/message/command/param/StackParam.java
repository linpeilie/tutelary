package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.BaseCommandParam;
import com.tutelary.constants.CommandEnum;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_STACK_METHOD)
public class StackParam extends BaseCommandParam {

    private String className;

    private List<String> methodNames;

    private int times = 1;

}
