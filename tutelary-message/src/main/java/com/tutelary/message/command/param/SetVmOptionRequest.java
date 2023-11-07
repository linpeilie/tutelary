package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.common.CommandRequest;
import lombok.Data;

@Data
@ProtobufClass
public class SetVmOptionRequest extends CommandRequest {

    private String name;

    private String value;



}
