package com.tutelary.client.task.factory.thread;

import java.lang.instrument.Instrumentation;

import com.tutelary.client.command.thread.ThreadCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.ThreadListRequest;

public class ThreadTaskFactory implements WithParameterTaskFactory<ThreadListRequest> {

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_THREAD_LIST;
    }

    @Override
    public Task create(String taskId, Instrumentation inst, ThreadListRequest param) {
        ThreadCommand threadCommand = new ThreadCommand(param);
        return new DefaultTask(taskId, commandType(), threadCommand);
    }
}
