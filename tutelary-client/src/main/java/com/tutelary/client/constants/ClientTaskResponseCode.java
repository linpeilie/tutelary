package com.tutelary.client.constants;

import com.tutelary.common.constants.BusinessResponseCode;
import com.tutelary.common.constants.ResponseCode;

public enum ClientTaskResponseCode implements ResponseCode {

    CLIENT_TASK_CHANGE_FAILURE("task [ %s ] change task state failure, current state : [ %s ]", 2);

    ClientTaskResponseCode(String message, int args) {
        this.code = formatCode(BusinessResponseCode.CLIENT_TASK.getCodePrefix());
        this.message = message;
        this.args = args;
    }

    private final String code;
    private final String message;
    private final int args;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getArgs() {
        return args;
    }

}
