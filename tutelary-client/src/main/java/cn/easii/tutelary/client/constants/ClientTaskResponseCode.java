package cn.easii.tutelary.client.constants;

import cn.easii.tutelary.common.constants.BusinessResponseCode;
import cn.easii.tutelary.common.constants.ResponseCode;

public enum ClientTaskResponseCode implements ResponseCode {

    CLIENT_TASK_CHANGE_FAILURE("task [ %s ] change task state failure, current state : [ %s ]", 2),
    CLASSLOADER_NOT_FOUND("class loader [ %s ] not found", 1),
    CLASS_NOT_FOUND("class [ %s ] not found, class loader [ {} ]", 2);

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
