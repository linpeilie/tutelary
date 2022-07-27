package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
public class MemoryManager implements Serializable {

    private String name;

    private List<String> memoryPoolNames;

}
