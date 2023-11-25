package cn.easii.tutelary.client.task.factory.logger;

import cn.easii.tutelary.client.command.logger.UpdateLoggerLevelCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.UpdateLoggerLevelRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
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
