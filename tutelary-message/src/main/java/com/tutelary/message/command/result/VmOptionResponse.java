package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.VmOption;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.GET_VM_OPTION)
public class VmOptionResponse extends CommandResponse {

    private List<VmOption> options;

}
