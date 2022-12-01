package com.tutelary.client.task.factory.stack;

import com.tutelary.client.command.stack.StackCommand;
import com.tutelary.client.task.EnhanceTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.StackParam;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public class StackTaskFactory implements WithParameterTaskFactory<StackParam> {
    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_STACK_METHOD;
    }

    @Override
    public Task create(Session session, Instrumentation inst, StackParam param) {
        StackCommand stackCommand = new StackCommand(inst, param);
        return new EnhanceTask(session, commandType(), stackCommand);
    }

}
