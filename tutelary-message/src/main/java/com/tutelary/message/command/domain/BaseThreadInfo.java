package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class BaseThreadInfo implements Serializable {

    private long id;

    private String name;

    private String group;

    private int priority;

    private String state;

    private double cpu;

    private boolean daemon;

}
