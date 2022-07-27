package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class GarbageCollector implements Serializable {

    private String name;
    private long collectionCount;
    private long collectionTime;

}
