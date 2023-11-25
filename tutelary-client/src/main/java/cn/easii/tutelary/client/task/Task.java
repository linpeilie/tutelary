package cn.easii.tutelary.client.task;

import cn.easii.tutelary.message.CommandCancelResponse;

public interface Task {

    String getId();

    TaskState getState();

    boolean setState(TaskState updateState, TaskState expectState);

    void execute();

    CommandCancelResponse cancel();

}
