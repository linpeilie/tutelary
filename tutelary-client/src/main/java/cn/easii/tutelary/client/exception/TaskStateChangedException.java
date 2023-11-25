package cn.easii.tutelary.client.exception;

import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.client.constants.ClientTaskResponseCode;
import cn.easii.tutelary.client.task.TaskState;

public class TaskStateChangedException extends BaseException {
    public TaskStateChangedException(String taskId, TaskState taskState) {
        super(ClientTaskResponseCode.CLIENT_TASK_CHANGE_FAILURE, taskId, taskState);
    }
}
