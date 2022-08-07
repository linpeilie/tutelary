package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class ThreadCountInfo implements Serializable {

    private int count;
    private int daemonCount;
    private int peakCount;
    private long startedCount;
    private int deadlockCount;

}
