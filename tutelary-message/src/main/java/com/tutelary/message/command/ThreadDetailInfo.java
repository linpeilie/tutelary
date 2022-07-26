package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class ThreadDetailInfo implements Serializable {

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

}
