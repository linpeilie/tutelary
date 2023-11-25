package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.UpdateLoggerLevelCommand;
import cn.easii.tutelary.command.AbstractSingleCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.dao.UpdateLoggerLevelCommandDAO;
import cn.easii.tutelary.message.command.param.UpdateLoggerLevelRequest;
import cn.easii.tutelary.message.command.result.UpdateLoggerLevelResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.updateLoggerLevel)
public class UpdateLoggerLevelCommandExec
    extends AbstractSingleCommandExecuteAdapter<UpdateLoggerLevelRequest, UpdateLoggerLevelResponse, UpdateLoggerLevelCommand> {
    public UpdateLoggerLevelCommandExec(UpdateLoggerLevelCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.updateLoggerLevel;
    }
}
