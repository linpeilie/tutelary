package com.tutelary.client.processor;

import cn.hutool.core.util.ServiceLoaderUtil;
import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.TaskExecutor;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.extension.ExtensionExecutor;
import com.tutelary.common.extension.ExtensionRegister;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.CommandExecuteRequest;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.remoting.api.Channel;
import java.io.IOException;
import java.util.List;

public class ClientCommandProcessor extends AbstractMessageProcessor<CommandExecuteRequest> {

    private static final Log LOGGER = LogFactory.get(ClientCommandProcessor.class);

    private static final int COMMAND_TIMEOUT = 1000;

    private final ExtensionExecutor extensionExecutor = new ExtensionExecutor();

    private final TaskExecutor taskExecutor = new TaskExecutor();

    public ClientCommandProcessor() {
        LOGGER.info("ClientCommandProcessor started");
        loadTaskFactories();
    }

    private static void loadTaskFactories() {
        List<TaskFactory> taskFactories =
            ServiceLoaderUtil.loadList(TaskFactory.class, ClassLoaderWrapper.getAgentClassLoader());
        LOGGER.info("taskFactories : {}", taskFactories);
        taskFactories.forEach(ExtensionRegister::doRegistration);
    }

    @Override
    protected void process(Channel channel, CommandExecuteRequest message) {
        TaskFactory taskFactory = extensionExecutor.getExtension(TaskFactory.class, message.getCode());
        if (taskFactory == null) {
            LOGGER.warn("unsupported command : {}", message.getCode());
            return;
        }
        LOGGER.debug("execute command : {}", message);
        Any param = message.getParam();
        try {
            CommandRequest commandRequest = (CommandRequest) param.unpack(taskFactory.parameterClass());
            LOGGER.debug("execute command : {}, task id : {}, param : {}", message.getCode(), message.getTaskId(),
                commandRequest
            );
            Task task = taskFactory.create(message.getTaskId(), ClientBootstrap.INSTRUMENTATION, commandRequest);
            taskExecutor.executeTask(task);
        } catch (IOException e) {
            // TODO
            throw new RuntimeException(e);
        }
    }

}
