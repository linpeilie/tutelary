package com.tutelary.client.task;

import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.command.AbstractEnhanceCommand;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.client.thread.AgentNamedThreadFactory;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.thread.NamedThreadFactory;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.CommandExecuteResponse;
import com.tutelary.message.command.result.EnhanceCommandComplete;
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
