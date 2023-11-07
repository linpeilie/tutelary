package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.common.CommandResponse;
import com.tutelary.message.command.domain.VmOption;
import lombok.Data;

@Data
@ProtobufClass
public class SetVmOptionResponse extends CommandResponse {

    private VmOption previousVmOption;

    private VmOption latestVmOption;

}
