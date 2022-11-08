package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
public class GarbageCollector implements Serializable {

    private String name;

    private long collectionCount;

    private long collectionTime;

    private List<String> memoryPoolNames;

}
