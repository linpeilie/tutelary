package com.tutelary.command.ext;

import com.tutelary.bean.domain.UpdateLoggerLevelCommand;
import com.tutelary.command.AbstractSingleCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.dao.UpdateLoggerLevelCommandDAO;
import com.tutelary.message.command.param.UpdateLoggerLevelRequest;
import com.tutelary.message.command.result.UpdateLoggerLevelResponse;
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
