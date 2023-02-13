package com.tutelary.client.task.factory.thread;

import java.lang.instrument.Instrumentation;

import com.tutelary.client.command.thread.ThreadCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.ThreadListRequest;

@Extension(commandCode = CommandConstants.threadList)
public class ThreadTaskFactory implements TaskFactory<ThreadListRequest> {

    @Override
    public CommandEnum commandType() {
        return CommandEnum.THREAD_LIST;
    }

    @Override
    public Task create(String taskId, Instrumentation inst, ThreadListRequest param) {
        ThreadCommand threadCommand = new ThreadCommand(param);
        return new DefaultTask(taskId, commandType(), threadCommand);
    }
}
