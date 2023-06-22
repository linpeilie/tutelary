package com.tutelary.client.exception;

import com.tutelary.client.constants.ClientTaskResponseCode;
import com.tutelary.client.task.TaskState;
import com.tutelary.common.exception.BaseException;

public class TaskStateChangedException extends BaseException {
    public TaskStateChangedException(String taskId, TaskState taskState) {
        super(ClientTaskResponseCode.CLIENT_TASK_CHANGE_FAILURE, taskId, taskState);
    }
}
