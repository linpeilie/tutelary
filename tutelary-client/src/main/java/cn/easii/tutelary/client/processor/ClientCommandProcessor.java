package cn.easii.tutelary.client.processor;

import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.extension.ExtensionExecutor;
import cn.easii.tutelary.common.extension.ExtensionRegister;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.message.CommandExecuteRequest;
import cn.easii.tutelary.processor.AbstractMessageProcessor;
import cn.easii.tutelary.processor.MessageProcessor;
import cn.hutool.core.util.ServiceLoaderUtil;
import com.baidu.bjf.remoting.protobuf.Any;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.ClientBootstrap;
import cn.easii.tutelary.client.loader.ClassLoaderWrapper;
import cn.easii.tutelary.client.task.Task;
import cn.easii.tutelary.client.task.TaskExecutor;
import cn.easii.tutelary.client.task.factory.TaskFactory;
import cn.easii.tutelary.remoting.api.Channel;
import java.io.IOException;
import java.util.List;

@AutoService(MessageProcessor.class)
public class ClientCommandProcessor extends AbstractMessageProcessor<CommandExecuteRequest> {

    private static final Log LOGGER = LogFactory.get(ClientCommandProcessor.class);

    private static final int COMMAND_TIMEOUT = 1000;

    private final ExtensionExecutor extensionExecutor = new ExtensionExecutor();

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
            TaskExecutor.executeTask(task);
        } catch (IOException e) {
            // TODO
            throw new RuntimeException(e);
        }
    }

}
