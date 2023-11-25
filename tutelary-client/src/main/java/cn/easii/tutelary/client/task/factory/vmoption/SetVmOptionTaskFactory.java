package cn.easii.tutelary.client.task.factory.vmoption;

import cn.easii.tutelary.client.command.vmoption.SetVmOptionCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.SetVmOptionRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
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
