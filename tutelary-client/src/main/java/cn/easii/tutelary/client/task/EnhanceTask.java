package cn.easii.tutelary.client.task;

import cn.easii.tutelary.client.command.AbstractEnhanceCommand;
import cn.easii.tutelary.client.enhance.callback.RCallback;
import cn.easii.tutelary.client.thread.AgentNamedThreadFactory;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.CommandExecuteResponse;
import cn.easii.tutelary.message.command.result.EnhanceCommandComplete;
import com.baidu.bjf.remoting.protobuf.Any;
import cn.easii.tutelary.client.ClientBootstrap;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EnhanceTask extends AbstractTask {

    private static final Log LOG = LogFactory.get(EnhanceTask.class);

    private static final ExecutorService EXECUTOR =
        new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new LinkedBlockingQueue<>(),
            new AgentNamedThreadFactory("enhance-task"));

    public EnhanceTask(String taskId, CommandEnum commandInfo, AbstractEnhanceCommand command) {
        super(taskId, commandInfo, command);
    }

    @Override
    protected void executeBefore() {
        // 结果回调
        ((AbstractEnhanceCommand) command).registerResultCallback(new RCallback(o -> {
            EXECUTOR.submit(() -> {
                LOG.debug("command code : [ {} ], execute result : {}", commandInfo.getCommandCode(), o);
                CommandExecuteResponse responseMessage = new CommandExecuteResponse();
                responseMessage.setCode(commandInfo.getCommandCode());
                responseMessage.setTaskId(getId());
                try {
                    responseMessage.setData(Any.pack(o));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ClientBootstrap.sendData(responseMessage);
            });
        }));
        // 命令执行完成回调
        ((AbstractEnhanceCommand) command).completionHandler(() -> {
            EXECUTOR.submit(() -> {
                LOG.debug("command code : [ {} ], execute complete", commandInfo.getCommandCode());
                EnhanceCommandComplete enhanceCommandComplete = new EnhanceCommandComplete();
                enhanceCommandComplete.setCode(commandInfo.getCommandCode());
                CommandExecuteResponse responseMessage = new CommandExecuteResponse();
                responseMessage.setCode(CommandConstants.enhanceComplete);
                responseMessage.setTaskId(getId());
                try {
                    responseMessage.setData(Any.pack(enhanceCommandComplete));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ClientBootstrap.sendData(responseMessage);
            });
        });
    }

    @Override
    protected void complete(final Object commandResult) {
        EXECUTOR.submit(() -> {
            LOG.debug("command code : [ {} ], execute completed, result : [ {} ]", commandInfo.getCommandCode(),
                commandResult);
            CommandExecuteResponse responseMessage = new CommandExecuteResponse();
            responseMessage.setTaskId(getId());
            responseMessage.setCode(CommandConstants.enhanceAffect);
            responseMessage.setTimestamp(System.currentTimeMillis());
            try {
                responseMessage.setData(Any.pack(commandResult));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ClientBootstrap.sendData(responseMessage);
        });
    }
}
