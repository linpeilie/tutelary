package com.tutelary.client.task.factory.stack;

import java.lang.instrument.Instrumentation;

import com.tutelary.client.command.stack.StackCommand;
import com.tutelary.client.task.EnhanceTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.StackRequest;

public class StackTaskFactory implements WithParameterTaskFactory<StackRequest> {
    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_STACK_METHOD;
    }

    @Override
    public Task create(String taskId, Instrumentation inst, StackRequest param) {
        StackCommand stackCommand = new StackCommand(inst, param);
        return new EnhanceTask(taskId, commandType(), stackCommand);
    }

}
