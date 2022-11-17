package com.tutelary.client.task;

import com.tutelary.client.command.Command;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 可复用任务，同一命令，同时只执行一次，返回给所有的调用方
 */
public class ReusedTask extends AbstractTask {

    private static final Log LOG = LogFactory.get(ReusedTask.class);

    /**
     * 命令对应任务集合
     */
    private static final Map<Integer, ConcurrentLinkedQueue<ReusedTask>> QUEUE_MAP = new ConcurrentHashMap<>();

    public ReusedTask(CommandEnum commandInfo, Session session, Command command) {
        super(commandInfo, session, command);
        if (!QUEUE_MAP.containsKey(commandInfo.getCommandCode())) {
            QUEUE_MAP.putIfAbsent(commandInfo.getCommandCode(), new ConcurrentLinkedQueue<>());
        }
    }

    @Override
    protected void executeAfter(Object result) {
        ConcurrentLinkedQueue<ReusedTask> queue = QUEUE_MAP.get(commandInfo.getCommandCode());
        while (!queue.isEmpty()) {
            ReusedTask task = queue.peek();
            LOG.debug("current task : [ {} ], state : [ {} ]", task.getId(), task.getState());
            if (task.getId().equals(this.getId())) {
                queue.poll();
                continue;
            }
            boolean changeState = task.setState(TaskState.COMPLETED, TaskState.NEW);
            if (!changeState) {
                break;
            }
            queue.poll();
            LOG.debug("directly to finish task : [ {} ]", task.getId());
            task.complete(result);
        }
    }

    @Override
    public void execute() {
        ConcurrentLinkedQueue<ReusedTask> queue = QUEUE_MAP.get(commandInfo.getCommandCode());
        queue.add(this);
        ReusedTask head = queue.peek();
        if (head != null && head.getId().equals(this.getId())) {
            super.execute();
        }
    }
}
