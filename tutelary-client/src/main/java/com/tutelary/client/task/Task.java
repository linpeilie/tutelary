package com.tutelary.client.task;

import com.tutelary.common.function.InnerFunction;
import com.tutelary.message.CommandCancelResponse;

public interface Task {

    String getId();

    TaskState getState();

    boolean setState(TaskState updateState, TaskState expectState);

    void execute();

    CommandCancelResponse cancel();

}
