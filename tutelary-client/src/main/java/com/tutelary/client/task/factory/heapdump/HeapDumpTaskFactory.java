package com.tutelary.client.task.factory.heapdump;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.heapdump.HeapDumpCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.HeapDumpRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.heapDump)
public class HeapDumpTaskFactory implements TaskFactory<HeapDumpRequest> {
    @Override
    public Task create(final String taskId, final Instrumentation inst, final HeapDumpRequest param) {
        return new DefaultTask(taskId, commandType(), new HeapDumpCommand(param));
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.HEAP_DUMP;
    }
}
