package cn.easii.tutelary.client.task.factory.thread;

import cn.easii.tutelary.client.command.thread.ThreadDetailCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.ThreadDetailRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
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
