package com.tutelary.common;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class CommandResult implements Serializable {

    private int jobId;

    private int status;

    private String message;

}
