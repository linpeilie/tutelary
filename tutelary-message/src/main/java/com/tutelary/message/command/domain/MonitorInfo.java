package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class MonitorInfo implements Serializable {

    private int stackDepth;
    private StackTraceElement stackFrame;

}
