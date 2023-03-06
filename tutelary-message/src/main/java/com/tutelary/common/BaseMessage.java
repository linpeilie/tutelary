package com.tutelary.common;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Message;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public abstract class BaseMessage implements Serializable {

    private Metadata metadata;

    public byte getCmd() {
        Message message = getClass().getAnnotation(Message.class);
        return message.cmd();
    }

}
