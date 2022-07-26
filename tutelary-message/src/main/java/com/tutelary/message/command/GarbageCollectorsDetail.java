package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class GarbageCollectorsDetail implements Serializable {

    private String name;
    private Integer collectionCount;
    private Integer collectionTime;

}
