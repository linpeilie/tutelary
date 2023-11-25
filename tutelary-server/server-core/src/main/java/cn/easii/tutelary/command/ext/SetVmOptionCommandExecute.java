package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.InstanceSetVmOptionCommand;
import cn.easii.tutelary.command.AbstractSingleCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.dao.InstanceSetVmOptionCommandDAO;
import cn.easii.tutelary.message.command.param.SetVmOptionRequest;
import cn.easii.tutelary.message.command.result.SetVmOptionResponse;
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
