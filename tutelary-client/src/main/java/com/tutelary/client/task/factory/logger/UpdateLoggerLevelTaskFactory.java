package com.tutelary.client.task.factory.logger;

import com.tutelary.client.command.logger.UpdateLoggerLevelCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.UpdateLoggerLevelRequest;
import java.lang.instrument.Instrumentation;

@Extension(commandCode = CommandConstants.updateLoggerLevel)
public class UpdateLoggerLevelTaskFactory implements TaskFactory<UpdateLoggerLevelRequest> {
    @Override
    public Task create(String taskId, Instrumentation inst, UpdateLoggerLevelRequest param) {
        return new DefaultTask(taskId, commandType(), new UpdateLoggerLevelCommand(param, inst));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.UPDATE_LOGGER_LEVEL;
    }
}
