package cn.easii.tutelary.client.task.factory.decompile;

import cn.easii.tutelary.client.command.decompile.DecompileCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.DecompileRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.ClientBootstrap;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.decompile)
public class DecompileTaskFactory implements TaskFactory<DecompileRequest> {
    @Override
    public Task create(final String taskId, final Instrumentation inst, final DecompileRequest param) {
        return new DefaultTask(taskId, commandType(), new DecompileCommand(param, ClientBootstrap.INSTRUMENTATION));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.DECOMPILE;
    }
}
