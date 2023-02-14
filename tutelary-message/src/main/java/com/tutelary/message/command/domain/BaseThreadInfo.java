package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

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
