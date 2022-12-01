package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class StackTraceNode implements Serializable {

    private String declaringClass;

    private String methodName;

    private int lineNumber;

}
