package com.tutelary.command.ext;

import com.tutelary.bean.domain.StackMethodCommand;
import com.tutelary.command.AbstractEnhanceCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.dao.StackMethodCommandDAO;
import com.tutelary.message.command.param.StackRequest;
import com.tutelary.message.command.result.StackResponse;
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
