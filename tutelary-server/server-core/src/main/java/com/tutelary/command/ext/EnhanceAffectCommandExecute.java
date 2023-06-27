package com.tutelary.command.ext;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.command.AbstractCommandExecute;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.message.command.result.EnhanceAffect;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.enhanceAffect)
public class EnhanceAffectCommandExecute extends AbstractCommandExecute<CommandRequest, EnhanceAffect> {

    @Override
    public CommandTask createCommand(final String instanceId, final CommandRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void callResult(final String instanceId, final String taskId, final EnhanceAffect response) {
        commandTaskDAO.updateEnhanceAffect(taskId, response);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.enhanceAffect;
    }
}
