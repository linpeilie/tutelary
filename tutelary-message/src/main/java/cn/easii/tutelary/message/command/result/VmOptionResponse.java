package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.VmOption;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.GET_VM_OPTION)
public class VmOptionResponse extends CommandResponse {

    private List<VmOption> options;

}
