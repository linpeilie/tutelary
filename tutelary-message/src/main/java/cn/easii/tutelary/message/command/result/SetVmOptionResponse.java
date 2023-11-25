package cn.easii.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.VmOption;
import lombok.Data;

@Data
@ProtobufClass
public class SetVmOptionResponse extends CommandResponse {

    private VmOption previousVmOption;

    private VmOption latestVmOption;

}
