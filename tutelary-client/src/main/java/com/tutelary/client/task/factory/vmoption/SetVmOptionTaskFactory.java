package com.tutelary.client.task.factory.vmoption;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.vmoption.SetVmOptionCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.SetVmOptionRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.setVmOption)
public class SetVmOptionTaskFactory implements TaskFactory<SetVmOptionRequest> {
    @Override
    public Task create(String taskId, Instrumentation inst, SetVmOptionRequest param) {
        return new DefaultTask(taskId, commandType(), new SetVmOptionCommand(param));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.SET_VM_OPTION;
    }
}
