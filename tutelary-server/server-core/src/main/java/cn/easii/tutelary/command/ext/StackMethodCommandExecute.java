package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.StackMethodCommand;
import cn.easii.tutelary.command.AbstractEnhanceCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.dao.StackMethodCommandDAO;
import cn.easii.tutelary.message.command.param.StackRequest;
import cn.easii.tutelary.message.command.result.StackResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.stackMethod)
public class StackMethodCommandExecute extends AbstractEnhanceCommandExecuteAdapter<StackRequest, StackResponse, StackMethodCommand> {

    public StackMethodCommandExecute(final StackMethodCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandEnum.STACK_METHOD.getCommandCode();
    }
}
