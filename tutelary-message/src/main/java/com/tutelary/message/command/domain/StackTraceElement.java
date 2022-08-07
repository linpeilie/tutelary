package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class StackTraceElement implements Serializable {

    private String declaringClass;
    private String methodName;
    private String fileName;
    private int lineNumber;

}
