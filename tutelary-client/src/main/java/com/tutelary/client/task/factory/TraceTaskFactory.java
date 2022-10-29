package com.tutelary.client.task.factory;

import cn.hutool.json.JSONUtil;
import com.tutelary.annotation.Command;
import com.tutelary.client.command.TraceCommand;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.client.task.EnhanceTask;
import com.tutelary.client.task.Task;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.EnhanceAffect;
import com.tutelary.message.command.TraceParam;
import com.tutelary.message.command.TraceResult;
import com.tutelary.session.Session;
import io.netty.channel.ChannelHandlerContext;

import java.lang.instrument.Instrumentation;

public class TraceTaskFactory implements TaskFactory<TraceParam> {

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

    @Override
    public Class<TraceParam> paramClass() {
        return TraceParam.class;
    }
}
