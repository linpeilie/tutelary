package cn.easii.tutelary.client.task;

import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.constants.CommandEnum;
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

    public ReusedTask(String taskId, CommandEnum commandInfo, Command command) {
        super(taskId, commandInfo, command);
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
