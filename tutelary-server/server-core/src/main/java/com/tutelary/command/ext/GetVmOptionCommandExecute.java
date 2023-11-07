package com.tutelary.command.ext;

import com.tutelary.command.AbstractSingleTemporaryCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.message.command.param.VmOptionRequest;
import com.tutelary.message.command.result.VmOptionResponse;
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
