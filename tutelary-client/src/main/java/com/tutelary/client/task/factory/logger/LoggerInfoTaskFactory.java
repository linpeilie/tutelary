package com.tutelary.client.task.factory.logger;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.logger.LoggerInfoCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.LoggerInfoRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.loggerInfo)
public class LoggerInfoTaskFactory implements TaskFactory<LoggerInfoRequest> {
    @Override
    public Task create(final String taskId, final Instrumentation inst, final LoggerInfoRequest param) {
        return new DefaultTask(taskId, commandType(), new LoggerInfoCommand(param, inst));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.LOGGER_INFO;
    }
}
