package com.tutelary.client.task.factory.thread;

import java.lang.instrument.Instrumentation;

import com.tutelary.client.command.thread.ThreadDetailCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.ThreadDetailRequest;

public class ThreadDetailTaskFactory implements WithParameterTaskFactory<ThreadDetailRequest> {
    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_THREAD_DETAIL;
    }

    @Override
    public Task create(String taskId, Instrumentation inst, ThreadDetailRequest param) {
        ThreadDetailCommand command = new ThreadDetailCommand(param);
        return new DefaultTask(taskId, commandType(), command);
    }
}
