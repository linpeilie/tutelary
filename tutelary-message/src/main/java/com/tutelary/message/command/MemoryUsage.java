package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class MemoryUsage implements Serializable {

    private String name;
    private long init;
    private long used;
    private long committed;
    private long max;

}
