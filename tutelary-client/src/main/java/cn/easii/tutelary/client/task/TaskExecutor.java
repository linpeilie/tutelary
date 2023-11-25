package cn.easii.tutelary.client.task;

import cn.easii.tutelary.client.task.event.TaskStateChangeEvents;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.message.CommandCancelResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskExecutor {

    private static final Log LOG = LogFactory.get(TaskExecutor.class);

    private static final TaskSerializeNumberCache serialNumberCache = new TaskSerializeNumberCache();

    private static final Map<String, Task> TASK_MAP = new ConcurrentHashMap<>();

    static {
        TaskStateChangeEvents.register(event -> {
            switch (event.getCurrentState()) {
                case COMPLETED:
                case CANCEL:
                    TASK_MAP.remove(event.getTaskId());
            }
        });
    }

    public static void executeTask(Task task) {
        if (TASK_MAP.putIfAbsent(task.getId(), task) == null) {
            task.execute();
        } else {
            LOG.warn("task : {} canceled, cause : repeated task", task.getId());
        }
    }

    public static CommandCancelResponse cancelTask(String taskId) {
        final Task task = TASK_MAP.get(taskId);
        if (task == null) {
            return CommandCancelResponse.cancelFailure("task does not exist or has been completed/canceled");
        }
        return task.cancel();
    }

}
