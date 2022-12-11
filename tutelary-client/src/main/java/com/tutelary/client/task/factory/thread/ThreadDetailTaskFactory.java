package com.tutelary.client.task.factory.thread;

import com.tutelary.client.command.thread.ThreadDetailCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.ThreadDetailParam;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public class ThreadDetailTaskFactory implements WithParameterTaskFactory<ThreadDetailParam> {
    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_THREAD_DETAIL;
    }

    @Override
    public Task create(Session session, Instrumentation inst, ThreadDetailParam param) {
        ThreadDetailCommand command = new ThreadDetailCommand(param);
        return new DefaultTask(commandType(), session, command);
    }
}
