package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.common.CommandResponse;
import lombok.Data;

@ProtobufClass
@Data
public class EnhanceCommandComplete extends CommandResponse {

    private Integer code;

}
