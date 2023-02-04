package com.tutelary.client.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.TaskExecutor;
import com.tutelary.client.task.factory.NonParameterTaskFactory;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.client.task.factory.WithParameterTaskFactory;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.CommandExecuteRequest;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.remoting.api.Channel;

import cn.hutool.core.util.ServiceLoaderUtil;

public class ClientCommandProcessor extends AbstractMessageProcessor<CommandExecuteRequest> {

    private static final Log LOGGER = LogFactory.get(ClientCommandProcessor.class);

    private static final int COMMAND_TIMEOUT = 1000;

    public static final Map<Integer, TaskFactory> TASK_FACTORY_MAP = new HashMap<>();

    private final TaskExecutor taskExecutor = new TaskExecutor();

    public ClientCommandProcessor() {
        LOGGER.info("ClientCommandProcessor started");
        loadTaskFactories();
    }

    private static void loadTaskFactories() {
        List<TaskFactory> taskFactories =
            ServiceLoaderUtil.loadList(TaskFactory.class, ClassLoaderWrapper.getAgentClassLoader());
        LOGGER.info("taskFactories : {}", taskFactories);
        taskFactories
            .forEach(taskFactory -> TASK_FACTORY_MAP.put(taskFactory.commandType().getCommandCode(), taskFactory));
    }

    @Override
    protected void process(Channel channel, CommandExecuteRequest message) {
        // 执行任务
        TaskFactory taskFactory = TASK_FACTORY_MAP.get(message.getCode());
        if (taskFactory == null) {
            LOGGER.warn("unsupported command : {}", message.getCode());
            return;
        }
        LOGGER.debug("execute command : {}", message);
        Task task = null;
        if (taskFactory instanceof WithParameterTaskFactory) {
            task = createTaskWithParameter((WithParameterTaskFactory)taskFactory, message);
        } else {
            task = createTaskWithoutParameter((NonParameterTaskFactory)taskFactory, message);
        }

        taskExecutor.executeTask(task);
    }

    private Task createTaskWithParameter(WithParameterTaskFactory taskFactory, CommandExecuteRequest message) {
        // 转换入参
        CommandRequest param = null;
        try {
            param = (CommandRequest)message.getParam().unpack(taskFactory.parameterClass());
            LOGGER.debug("execute command param : {}", param);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskFactory.create(message.getTaskId(), ClientBootstrap.INSTRUMENTATION, param);
    }

    private Task createTaskWithoutParameter(NonParameterTaskFactory taskFactory, CommandExecuteRequest message) {
        return taskFactory.create(message.getTaskId(), ClientBootstrap.INSTRUMENTATION);
    }

}
