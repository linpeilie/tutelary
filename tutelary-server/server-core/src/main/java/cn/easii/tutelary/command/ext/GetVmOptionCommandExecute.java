package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.command.AbstractSingleTemporaryCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.message.command.param.VmOptionRequest;
import cn.easii.tutelary.message.command.result.VmOptionResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.getVmOption)
public class GetVmOptionCommandExecute
    extends AbstractSingleTemporaryCommandExecuteAdapter<VmOptionRequest, VmOptionResponse> {
    @Override
    public Integer commandCode() {
        return CommandConstants.getVmOption;
    }
}
