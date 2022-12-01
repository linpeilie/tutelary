package com.tutelary.client.task.factory.trace;

import com.tutelary.client.command.trace.TraceCommand;
import com.tutelary.client.task.EnhanceTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.TraceParam;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public class TraceTaskFactory implements WithParameterTaskFactory<TraceParam> {

    @Override
    public Task create(Session session, Instrumentation inst, TraceParam param) {
        // 生成命令
        TraceCommand traceCommand = new TraceCommand(inst, param);
        // 构建任务
        return new EnhanceTask(session, commandType(), traceCommand);
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_TRACE_METHOD;
    }
}
