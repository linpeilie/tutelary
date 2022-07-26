package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class MemoryInfo implements Serializable {

    private MemoryUsage headMemoryUsage;
    private MemoryUsage noHeadMemoryUsage;
    private long pendingFinalizeCount;

}
