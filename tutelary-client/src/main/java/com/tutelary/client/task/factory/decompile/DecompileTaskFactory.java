package com.tutelary.client.task.factory.decompile;

import com.google.auto.service.AutoService;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.command.decompile.DecompileCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.DecompileRequest;
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
