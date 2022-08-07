package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ProtobufClass
public class ThreadInfo implements Serializable {

    private long id;
    private String name;
    private String group;
    private int priority;
    private String state;
    private double cpu;
    private long deltaTime;
    private long time;
    private boolean interrupted;
    private boolean daemon;
    private long blockedTime;
    private long blockedCount;
    private long waitedTime;
    private long waitedCount;
    private String lockName;
    private long lockOwnerName;
    private boolean inNative;
    private boolean suspended;
    private List<StackTraceElement> stackTrace;
    private List<MonitorInfo> lockedMonitors;

}
