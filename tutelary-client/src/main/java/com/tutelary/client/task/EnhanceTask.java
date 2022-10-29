package com.tutelary.client.task;

import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.NamedThreadFactory;
import com.tutelary.client.command.AbstractEnhanceCommand;
import com.tutelary.client.enhance.callback.CompletionHandler;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.message.command.EnhanceAffect;
import com.tutelary.message.command.EnhanceCommandComplete;
import com.tutelary.session.Session;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class EnhanceTask implements Task {

    private static final Log LOG = LogFactory.get(EnhanceTask.class);

    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            1,
            Runtime.getRuntime().availableProcessors(),
            1,
            TimeUnit.HOURS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("task-executor"));

    private final AbstractEnhanceCommand command;

    private final CommandEnum commandInfo;

    private final Session session;

    public EnhanceTask(Session session, CommandEnum commandInfo, AbstractEnhanceCommand command) {
        this.session = session;
        this.commandInfo = commandInfo;
        this.command = command;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void execute() {
        // 结果回调
        command.registerResultCallback(new RCallback(o -> {
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
        command.completionHandler(() -> {
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
        // 类加强回调
        CompletableFuture.supplyAsync(command::execute, EXECUTOR)
                .thenAccept(enhanceAffect -> {
                    LOG.debug("session : [ {} ], command code : [ {} ], enhance success, enhance affect : {}",
                            session.getSessionId(), commandInfo.getCommandCode(), enhanceAffect);
                    ClientCommandResponseMessage responseMessage = new ClientCommandResponseMessage();
                    responseMessage.setSessionId(session.getSessionId());
                    responseMessage.setCommandType(CommandEnum.TUTELARY_ENHANCE.getType());
                    responseMessage.setCommandCode(CommandEnum.TUTELARY_ENHANCE.getCommandCode());
                    try {
                        responseMessage.setData(Any.pack(enhanceAffect));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    session.sendData(responseMessage);
                });
    }
}
