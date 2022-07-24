package com.tutelary.message;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.ResponseBaseMessage;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.ERROR)
public class ErrorMessage extends ResponseBaseMessage {

    private String lastCmd;

}
