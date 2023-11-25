package cn.easii.tutelary.client.task.factory.trace;

import cn.easii.tutelary.client.command.trace.TraceCommand;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.TraceRequest;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.EnhanceTask;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.factory.TaskFactory;
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
