package cn.easii.tutelary.client.task.factory.getstatic;

import cn.easii.tutelary.client.command.getstatic.GetStaticCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.GetStaticRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.getStatic)
public class GetStaticTaskFactory implements TaskFactory<GetStaticRequest> {
    @Override
    public Task create(String taskId, Instrumentation inst, GetStaticRequest param) {
        return new DefaultTask(taskId, commandType(), new GetStaticCommand(param, inst));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.GET_STATIC;
    }
}
