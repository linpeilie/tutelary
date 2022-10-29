package com.tutelary.client.task;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public class TaskStore {

    private final ConcurrentMap<String, Task> tasks = Maps.newConcurrentMap();

    public boolean addTask(Task task) {
        return tasks.putIfAbsent(task.getId(), task) == null;
    }

    public void remove(String id) {
        tasks.remove(id);
    }

    public Task getTask(String id) {
        return tasks.get(id);
    }

}
