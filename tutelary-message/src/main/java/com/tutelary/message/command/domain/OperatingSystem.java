package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class OperatingSystem implements Serializable {

    private String os;
    private String arch;
    private int processorsCount;
    private double loadAverage;
    private String version;

}
