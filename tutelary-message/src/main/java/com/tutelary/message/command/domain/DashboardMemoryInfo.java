package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
public class DashboardMemoryInfo implements Serializable {

    private List<MemoryUsage> heap;
    private List<MemoryUsage> nonHeap;
    private List<MemoryUsage> bufferPool;

}
