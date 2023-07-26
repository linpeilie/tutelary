package com.tutelary.common;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.enums.StateEnum;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public class CommandResponse implements Serializable {

    private int jobId;

    private int state = StateEnum.SUCCESS.getValue();

    private String message;

    public void failed(String message) {
        this.state = StateEnum.FAILURE.getValue();
        this.message = message;
    }

}
