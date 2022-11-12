package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.common.CommandResult;
import lombok.Data;

@ProtobufClass
@Data
public class EnhanceCommandComplete extends CommandResult {

    private String sessionId;

    private Integer commandCode;

}
