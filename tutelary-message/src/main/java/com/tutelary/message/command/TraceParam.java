package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.constants.CommandEnum;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_TRACE_METHOD)
public class TraceParam {

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
