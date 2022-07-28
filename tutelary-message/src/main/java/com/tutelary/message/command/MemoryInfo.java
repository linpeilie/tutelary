package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class MemoryInfo implements Serializable {

    private MemoryUsage heapMemoryUsage;
    private MemoryUsage noHeapMemoryUsage;
    private long pendingFinalizeCount;

}
