package com.tutelary.client.task;

import java.io.IOException;

import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.command.AbstractEnhanceCommand;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.CommandExecuteResponse;
import com.tutelary.message.command.result.EnhanceCommandComplete;

public class EnhanceTask extends AbstractTask {

    private static final Log LOG = LogFactory.get(EnhanceTask.class);

    public EnhanceTask(String taskId, CommandEnum commandInfo, AbstractEnhanceCommand command) {
        super(taskId, commandInfo, command);
    }

    @Override
    protected void executeBefore() {
        // 结果回调
        ((AbstractEnhanceCommand)command).registerResultCallback(new RCallback(o -> {
            LOG.debug("command code : [ {} ], execute result : {}", commandInfo.getCommandCode(), o);
            CommandExecuteResponse responseMessage = new CommandExecuteResponse();
            responseMessage.setCode(commandInfo.getCommandCode());
            try {
                responseMessage.setData(Any.pack(o));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ClientBootstrap.sendData(responseMessage);
        }));
        // 命令执行完成回调
        ((AbstractEnhanceCommand)command).completionHandler(() -> {
            LOG.debug("command code : [ {} ], execute complete", commandInfo.getCommandCode());
            EnhanceCommandComplete enhanceCommandComplete = new EnhanceCommandComplete();
            enhanceCommandComplete.setCode(commandInfo.getCommandCode());
            CommandExecuteResponse responseMessage = new CommandExecuteResponse();
            responseMessage.setCode(CommandEnum.TUTELARY_ENHANCE_TASK_COMPLETE.getCommandCode());
            try {
                responseMessage.setData(Any.pack(enhanceCommandComplete));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ClientBootstrap.sendData(responseMessage);
        });
    }

    @Override
    protected void complete(Object commandResult) {
        LOG.debug("command code : [ {} ], enhance success, enhance affect : {}", commandInfo.getCommandCode(),
            commandResult);
        CommandExecuteResponse responseMessage = new CommandExecuteResponse();
        responseMessage.setCode(CommandEnum.TUTELARY_ENHANCE.getCommandCode());
        try {
            responseMessage.setData(Any.pack(commandResult));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientBootstrap.sendData(responseMessage);
    }
}
