package cn.easii.tutelary.client.task;

import com.google.common.collect.EvictingQueue;

public class TaskSerializeNumberCache {

    private static final int DEFAULT_MAX_CAPACITY = 256;

    private final EvictingQueue<String> tasks = EvictingQueue.create(DEFAULT_MAX_CAPACITY);

    public boolean add(String taskId) {
        if (tasks.contains(taskId)) {
            return false;
        }
        synchronized (tasks) {
            if (tasks.contains(taskId)) {
                return false;
            }
            return tasks.add(taskId);
        }
    }

}
