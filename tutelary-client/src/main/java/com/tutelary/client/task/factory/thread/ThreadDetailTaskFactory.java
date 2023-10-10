package com.tutelary.client.task.factory.thread;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.thread.ThreadDetailCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.ThreadDetailRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.threadDetail)
public class ThreadDetailTaskFactory implements TaskFactory<ThreadDetailRequest> {
    @Override
    public CommandEnum commandType() {
        return CommandEnum.THREAD_DETAIL;
    }

    @Override
    public Task create(String taskId, Instrumentation inst, ThreadDetailRequest param) {
        ThreadDetailCommand command = new ThreadDetailCommand(param);
        return new DefaultTask(taskId, commandType(), command);
    }
}
