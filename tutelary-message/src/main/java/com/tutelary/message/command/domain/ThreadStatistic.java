package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class ThreadStatistic implements Serializable {

    private int threadCount;

    private int peakThreadCount;

    private int daemonThreadCount;

    private long totalStartedThreadCount;

}
