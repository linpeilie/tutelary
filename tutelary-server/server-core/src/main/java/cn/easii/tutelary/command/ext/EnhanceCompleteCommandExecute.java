package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.command.AbstractCommandExecute;
import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.message.command.result.EnhanceCommandComplete;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.enhanceComplete)
public class EnhanceCompleteCommandExecute extends AbstractCommandExecute<CommandRequest, EnhanceCommandComplete> {

    @Override
    public CommandTask createCommand(final String instanceId, final CommandRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void callResult(final String instanceId, final String taskId, final EnhanceCommandComplete response) {
        commandTaskDAO.commandTaskComplete(taskId);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.enhanceComplete;
    }
}
