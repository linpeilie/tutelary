package com.tutelary.command.ext;

import com.tutelary.bean.domain.InstanceSetVmOptionCommand;
import com.tutelary.command.AbstractSingleCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.dao.InstanceSetVmOptionCommandDAO;
import com.tutelary.message.command.param.SetVmOptionRequest;
import com.tutelary.message.command.result.SetVmOptionResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.setVmOption)
public class SetVmOptionCommandExecute
extends AbstractSingleCommandExecuteAdapter<SetVmOptionRequest, SetVmOptionResponse, InstanceSetVmOptionCommand> {

    public SetVmOptionCommandExecute(InstanceSetVmOptionCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.setVmOption;
    }
}
