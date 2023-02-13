package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandRequest;
import com.tutelary.constants.CommandEnum;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TRACE_METHOD)
public class TraceRequest extends CommandRequest {

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private List<String> methodNames;

    /**
     * 执行次数
     */
    private int times = 1;

    /**
     * 方法执行耗时
     */
    private int cost = 0;

}
