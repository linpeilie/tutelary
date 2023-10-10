package com.tutelary.client.task.factory.trace;

import com.google.auto.service.AutoService;
import com.tutelary.client.command.trace.TraceCommand;
import com.tutelary.client.task.EnhanceTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.TraceRequest;
import java.lang.instrument.Instrumentation;

@AutoService(TaskFactory.class)
@Extension(commandCode = CommandConstants.traceMethod)
public class TraceTaskFactory implements TaskFactory<TraceRequest> {

    @Override
    public Task create(String taskId, Instrumentation inst, TraceRequest param) {
        // 生成命令
        TraceCommand traceCommand = new TraceCommand(inst, param);
        // 构建任务
        return new EnhanceTask(taskId, commandType(), traceCommand);
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TRACE_METHOD;
    }
}
