package com.tutelary.client.processor;

import cn.hutool.core.util.ServiceLoaderUtil;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.ClientCommandRequestMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.session.Session;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientCommandProcessor extends AbstractMessageProcessor<ClientCommandRequestMessage> {

    private static final Log LOGGER = LogFactory.get(ClientCommandProcessor.class);

    private static final int COMMAND_TIMEOUT = 1000;

    public static final Map<Integer, TaskFactory> TASK_FACTORY_MAP = new HashMap<>();

    public ClientCommandProcessor() {
        LOGGER.info("ClientCommandProcessor started");
        loadTaskFactories();
    }

    private static void loadTaskFactories() {
        List<TaskFactory> taskFactories = ServiceLoaderUtil.loadList(TaskFactory.class, ClassLoaderWrapper.getAgentClassLoader());
        LOGGER.info("taskFactories : {}",  taskFactories);
        taskFactories.forEach(taskFactory -> TASK_FACTORY_MAP.put(taskFactory.commandType().getCommandCode(), taskFactory));
    }

    @Override
    protected void process(ChannelHandlerContext ctx, ClientCommandRequestMessage message) {
        // 执行任务
        TaskFactory taskFactory = TASK_FACTORY_MAP.get(message.getCommandCode());
        // 转换入参
        Object param = null;
        try {
            LOGGER.debug("execute command param : {}", message);
            param = message.getParam().unpack(taskFactory.paramClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Session session = new Session(message.getSessionId(), ctx.channel());
        LOGGER.debug("session : [ {} ], execute command : {}", session, message);
        Task task = taskFactory.create(session, ClientBootstrap.INSTRUMENTATION, param);
        task.execute();
    }

    @Override
    public Class<ClientCommandRequestMessage> getCmdClass() {
        return ClientCommandRequestMessage.class;
    }
}
