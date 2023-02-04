package com.tutelary.client.task;

import cn.hutool.core.lang.UUID;
import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.NamedThreadFactory;
import com.tutelary.client.command.Command;
import com.tutelary.client.exception.TaskStateChangedException;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.CommandExecuteResponse;
import com.tutelary.session.Session;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractTask implements Task {

    private static final Log LOG = LogFactory.get(AbstractTask.class);

    protected static final ExecutorService EXECUTOR =
        new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 1, TimeUnit.HOURS,
            new LinkedBlockingQueue<>(), new NamedThreadFactory("task-executor"));

    private final String id;

    protected final CommandEnum commandInfo;

    protected final Command command;

    private final AtomicReference<TaskState> state = new AtomicReference<>(TaskState.NEW);

    public AbstractTask(String taskId, CommandEnum commandInfo, Command command) {
        this.id = taskId;
        this.commandInfo = commandInfo;
        this.command = command;
    }

    @Override
    public String getId() {
        return id;
    }

    protected void executeBefore() {}

    protected void complete(Object commandResult) {
        LOG.debug("session : [ {} ], command code : [ {} ], execute completed, result : [ {} ]",
            commandInfo.getCommandCode(), commandResult);
        CommandExecuteResponse responseMessage = new CommandExecuteResponse();
        responseMessage.setCode(commandInfo.getCommandCode());
        responseMessage.setTimestamp(System.currentTimeMillis());
        try {
            responseMessage.setData(Any.pack(commandResult));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientBootstrap.sendData(responseMessage);
    }

    protected void failure(String message) {
        LOG.error("task : [ {} ], execute failure : {}", getId(), message);
        CommandExecuteResponse responseMessage = new CommandExecuteResponse();
        responseMessage.setStatus(Boolean.FALSE);
        responseMessage.setMessage(message);
        responseMessage.setCode(commandInfo.getCommandCode());
        ClientBootstrap.sendData(responseMessage);
    }

    protected void executeAfter(Object result) {}

    @Override
    public TaskState getState() {
        return state.get();
    }

    @Override
    public boolean setState(TaskState updateState, TaskState expectState) {
        return state.compareAndSet(expectState, updateState);
    }

    private void changeState(TaskState updateState, TaskState expectState) {
        boolean changeState = setState(updateState, expectState);
        if (!changeState) {
            LOG.warn("task id : [ {} ], change task state failure", getId());
            throw new TaskStateChangedException("task current state : " + getState());
        }
    }

    private Object executeWrapper() {
        changeState(TaskState.RUNNING, TaskState.NEW);
        executeBefore();
        return command.execute();
    }

    @Override
    public void execute() {
        CompletableFuture.supplyAsync(this::executeWrapper, EXECUTOR).thenAccept(obj -> {
            changeState(TaskState.COMPLETED, TaskState.RUNNING);
            this.complete(obj);
            this.executeAfter(obj);
        }).exceptionally(throwable -> {
            if (throwable instanceof TaskStateChangedException) {
                return null;
            }
            failure(throwable.getMessage());
            return null;
        });
    }
}
