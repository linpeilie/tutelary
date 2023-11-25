package cn.easii.tutelary.client.task.factory.stack;

import cn.easii.tutelary.client.command.stack.StackCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.StackRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.EnhanceTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.stackMethod)
public class StackTaskFactory implements TaskFactory<StackRequest> {
    @Override
    public CommandEnum commandType() {
        return CommandEnum.STACK_METHOD;
    }

    @Override
    public Task create(String taskId, Instrumentation inst, StackRequest param) {
        StackCommand stackCommand = new StackCommand(inst, param);
        return new EnhanceTask(taskId, commandType(), stackCommand);
    }

}
