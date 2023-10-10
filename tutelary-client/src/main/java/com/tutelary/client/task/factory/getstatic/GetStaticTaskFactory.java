package com.tutelary.client.task.factory.getstatic;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.getstatic.GetStaticCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.GetStaticRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.getStatic)
public class GetStaticTaskFactory implements TaskFactory<GetStaticRequest> {
    @Override
    public Task create(String taskId, Instrumentation inst, GetStaticRequest param) {
        return new DefaultTask(taskId, commandType(), new GetStaticCommand(param, inst));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.GET_STATIC;
    }
}
