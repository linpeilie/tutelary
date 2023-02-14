package com.tutelary.client.task.factory.stack;

import com.tutelary.client.command.stack.StackCommand;
import com.tutelary.client.task.EnhanceTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.StackRequest;
import java.lang.instrument.Instrumentation;

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
