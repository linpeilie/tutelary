package cn.easii.tutelary.client.task.factory.logger;

import cn.easii.tutelary.client.command.logger.LoggerInfoCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.LoggerInfoRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
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
