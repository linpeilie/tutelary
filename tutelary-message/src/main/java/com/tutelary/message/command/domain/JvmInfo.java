package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
public class JvmInfo implements Serializable {

    private List<String> inputArguments;

    private String classPath;

    private String libraryPath;

    /**
     * Java 虚拟机实现供应商
     */
    private String vmVendor;

    /**
     * Java 虚拟机实现名称
     */
    private String vmName;

    /**
     * Java 虚拟机实现版本
     */
    private String vmVersion;

    /**
     * JDK 版本
     */
    private String jdkVersion;

    /**
     * JVM 启动时间戳
     */
    private long startTime;

    /**
     * 运行 Java 虚拟机的 CPU 时间，单位：毫秒
     * 如果平台不支持此操作，则返回 -1
     */
    private long processCpuTime;

}
