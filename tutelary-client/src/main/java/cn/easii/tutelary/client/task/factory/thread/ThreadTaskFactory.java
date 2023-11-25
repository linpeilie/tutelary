package cn.easii.tutelary.client.task.factory.thread;

import cn.easii.tutelary.client.command.thread.ThreadCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.ThreadListRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
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
