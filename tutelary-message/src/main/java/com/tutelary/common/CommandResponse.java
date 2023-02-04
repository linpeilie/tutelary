package com.tutelary.common;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.enums.StateEnum;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class CommandResponse implements Serializable {

    private int jobId;

    private int state;

    private String message;

    public void failed(String message) {
        this.state = StateEnum.FAILURE.getValue();
        this.message = message;
    }

}
