package cn.easii.tutelary.client.task.factory.vmoption;

import cn.easii.tutelary.client.command.vmoption.VmOptionCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.VmOptionRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.ReusedTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
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
