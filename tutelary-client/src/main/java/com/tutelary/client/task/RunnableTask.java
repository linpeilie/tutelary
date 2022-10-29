package com.tutelary.client.task;

public class RunnableTask implements Runnable {

    private final Task task;

    private RunnableTask(Task task) {
        this.task = task;
    }

    public static RunnableTask wrap(Task task) {
        return new RunnableTask(task);
    }

    @Override
    public void run() {
        task.execute();
    }
}
