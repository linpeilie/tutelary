package com.tutelary.client.task;

import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.thread.NamedThreadFactory;
import com.tutelary.client.command.Command;
import com.tutelary.client.exception.TaskStateChangedException;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.CommandExecuteResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractTask implements Task {

    protected static final ExecutorService EXECUTOR =
        new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 1, TimeUnit.HOURS,
            new LinkedBlockingQueue<>(), new NamedThreadFactory("task-executor")
        );
    private static final Log LOG = LogFactory.get(AbstractTask.class);
    protected final CommandEnum commandInfo;
    protected final Command command;
    private final String id;
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

    protected void executeBefore() {
    }

    protected void sendCommandResult(Object commandResult) {
        CommandExecuteResponse responseMessage = new CommandExecuteResponse();
        responseMessage.setTaskId(getId());
        responseMessage.setCode(commandInfo.getCommandCode());
        responseMessage.setTimestamp(System.currentTimeMillis());
        try {
            responseMessage.setData(Any.pack(commandResult));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientBootstrap.sendData(responseMessage);
    }

    protected void complete(Object commandResult) {
        LOG.debug("command code : [ {} ], execute completed, result : [ {} ]",
            commandInfo.getCommandCode(), commandResult
        );
        sendCommandResult(commandResult);
    }

    protected void failure(String message) {
        LOG.error("task : [ {} ], execute failure : {}", getId(), message);
        CommandExecuteResponse responseMessage = new CommandExecuteResponse();
        responseMessage.setStatus(Boolean.FALSE);
        responseMessage.setMessage(message);
        responseMessage.setTaskId(getId());
        responseMessage.setCode(commandInfo.getCommandCode());
        ClientBootstrap.sendData(responseMessage);
    }

    protected void executeAfter(Object result) {
    }

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
            throw new TaskStateChangedException(getId(), getState());
        }
    }

    private Object executeWrapper() {
        changeState(TaskState.RUNNING, TaskState.NEW);
        executeBefore();
        return toExecute();
    }

    protected Object toExecute() {
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
