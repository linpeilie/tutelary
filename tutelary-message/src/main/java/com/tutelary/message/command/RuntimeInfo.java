package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
public class RuntimeInfo implements Serializable {
    private String machineName;
    private String jvmStartTime;
    private String managementSpecVersion;
    private String specName;
    private String specVendor;
    private String specVersion;
    private String vmName;
    private String vmVendor;
    private String vmVersion;
    private List<String> inputArguments;
}
