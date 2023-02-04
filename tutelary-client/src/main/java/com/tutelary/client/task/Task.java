package com.tutelary.client.task;

public interface Task {

    String getId();

    TaskState getState();

    boolean setState(TaskState updateState, TaskState expectState);

    void execute();

}
