package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.common.CommandResult;
import com.tutelary.message.command.domain.HostBaseInfo;
import lombok.Data;

@Data
@ProtobufClass
public class InstanceBaseInfo extends CommandResult {

    private HostBaseInfo hostInfo;

}
