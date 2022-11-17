package com.tutelary.client.exception;

import com.tutelary.common.exception.BaseException;

public class TaskStateChangedException extends BaseException {
    public TaskStateChangedException(String errorMessage) {
        super(errorMessage);
    }
}
