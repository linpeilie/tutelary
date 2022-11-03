package com.tutelary.client.task;

import cn.hutool.core.lang.UUID;
import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.NamedThreadFactory;
import com.tutelary.client.command.Command;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.session.Session;

import java.io.IOException;
import java.util.concurrent.*;

public abstract class AbstractTask implements Task {

    private static final Log LOG = LogFactory.get(AbstractTask.class);

    protected static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            1,
            Runtime.getRuntime().availableProcessors(),
            1,
            TimeUnit.HOURS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("task-executor"));

    private final String id;

    protected final CommandEnum commandInfo;

    protected final Session session;

    protected final Command command;

    public AbstractTask(CommandEnum commandInfo, Session session, Command command) {
        this.id = UUID.randomUUID().toString(true);
        this.commandInfo = commandInfo;
        this.session = session;
        this.command = command;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void execute() {
        CompletableFuture.supplyAsync(command::execute, EXECUTOR)
                .thenAccept(commandResult -> {
                    LOG.debug("session : [ {} ], command code : [ {} ], execute completed, result : [ {} ]",
                            session.getSessionId(), commandInfo.getCommandCode(), commandResult);
                    ClientCommandResponseMessage responseMessage = new ClientCommandResponseMessage();
                    responseMessage.setSessionId(session.getSessionId());
                    responseMessage.setCommandType(commandInfo.getType());
                    responseMessage.setCommandCode(commandInfo.getCommandCode());
                    try {
                        responseMessage.setData(Any.pack(commandResult));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    session.sendData(responseMessage);
                });
    }
}
