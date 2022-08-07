package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class DashboardRuntime implements Serializable {

    private String javaHome;
    private String javaVersion;
    private String osName;
    private String osVersion;
    private int processors;
    private double systemLoadAverage;
    private long timestamp;
    private long uptime;

}
