package com.tutelary.common;

import java.io.Serializable;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Message;

import cn.hutool.core.lang.UUID;

@ProtobufClass
public abstract class BaseMessage implements Serializable {

    public byte getCmd() {
        Message message = getClass().getAnnotation(Message.class);
        return message.cmd();
    }

}
