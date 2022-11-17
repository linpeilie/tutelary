package com.tutelary.client.task;

import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.command.AbstractEnhanceCommand;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.message.command.result.EnhanceCommandComplete;
import com.tutelary.session.Session;

import java.io.IOException;
import java.util.concurrent.*;

public class EnhanceTask extends AbstractTask {

    private static final Log LOG = LogFactory.get(EnhanceTask.class);

    public EnhanceTask(Session session, CommandEnum commandInfo, AbstractEnhanceCommand command) {
        super(commandInfo, session, command);
    }

    @Override
    protected void executeBefore() {
        // 结果回调
        ((AbstractEnhanceCommand) command).registerResultCallback(new RCallback(o -> {
            LOG.debug("session : [ {} ], command code : [ {} ], execute result : {}",
                    session.getSessionId(), commandInfo.getCommandCode(), o);
            ClientCommandResponseMessage responseMessage = new ClientCommandResponseMessage();
            responseMessage.setSessionId(session.getSessionId());
            responseMessage.setCommandType(commandInfo.getType());
            responseMessage.setCommandCode(commandInfo.getCommandCode());
            try {
                responseMessage.setData(Any.pack(o));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            session.sendData(responseMessage);
        }));
        // 命令执行完成回调
        ((AbstractEnhanceCommand) command).completionHandler(() -> {
            LOG.debug("session : [ {} ], command code : [ {} ], execute complete",
                    session.getSessionId(), commandInfo.getCommandCode());
            EnhanceCommandComplete enhanceCommandComplete = new EnhanceCommandComplete();
            enhanceCommandComplete.setSessionId(session.getSessionId());
            enhanceCommandComplete.setCommandCode(commandInfo.getCommandCode());
            ClientCommandResponseMessage responseMessage = new ClientCommandResponseMessage();
            responseMessage.setCommandType(CommandEnum.TUTELARY_ENHANCE_TASK_COMPLETE.getType());
            responseMessage.setCommandCode(CommandEnum.TUTELARY_ENHANCE_TASK_COMPLETE.getCommandCode());
            responseMessage.setSessionId(session.getSessionId());
            try {
                responseMessage.setData(Any.pack(enhanceCommandComplete));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            session.sendData(responseMessage);
        });
    }

    @Override
    protected void complete(Object commandResult) {
        LOG.debug("session : [ {} ], command code : [ {} ], enhance success, enhance affect : {}",
                session.getSessionId(), commandInfo.getCommandCode(), commandResult);
        ClientCommandResponseMessage responseMessage = new ClientCommandResponseMessage();
        responseMessage.setSessionId(session.getSessionId());
        responseMessage.setCommandType(CommandEnum.TUTELARY_ENHANCE.getType());
        responseMessage.setCommandCode(CommandEnum.TUTELARY_ENHANCE.getCommandCode());
        try {
            responseMessage.setData(Any.pack(commandResult));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        session.sendData(responseMessage);
    }
}
