package com.tutelary.client.task.factory.vmoption;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.vmoption.VmOptionCommand;
import com.tutelary.client.task.ReusedTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.VmOptionRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.getVmOption)
public class GetVmOptionTaskFactory implements TaskFactory<VmOptionRequest> {
    @Override
    public Task create(String taskId, Instrumentation inst, VmOptionRequest param) {
        return new ReusedTask(taskId, commandType(), new VmOptionCommand(param));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.GET_VM_OPTION;
    }
}
