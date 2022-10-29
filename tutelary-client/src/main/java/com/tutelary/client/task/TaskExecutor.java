package com.tutelary.client.task;

import com.tutelary.message.ClientCommandRequestMessage;
import io.netty.channel.ChannelHandlerContext;

public class TaskExecutor {

    private static final TaskStore TASK_STORE = new TaskStore();

    public void executeTask(ChannelHandlerContext ctx, ClientCommandRequestMessage message) {

    }

}
