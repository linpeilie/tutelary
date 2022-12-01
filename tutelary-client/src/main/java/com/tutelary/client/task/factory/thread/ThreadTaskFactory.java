package com.tutelary.client.task.factory.thread;

import com.tutelary.client.command.thread.ThreadCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.ThreadListParam;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public class ThreadTaskFactory implements WithParameterTaskFactory<ThreadListParam> {

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_THREAD_LIST;
    }

    @Override
    public Task create(Session session, Instrumentation inst, ThreadListParam param) {
        ThreadCommand threadCommand = new ThreadCommand(param);
        return new DefaultTask(commandType(), session, threadCommand);
    }
}
