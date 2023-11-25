package cn.easii.tutelary.client.task.factory.heapdump;

import cn.easii.tutelary.client.command.heapdump.HeapDumpCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.HeapDumpRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.DefaultTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
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
