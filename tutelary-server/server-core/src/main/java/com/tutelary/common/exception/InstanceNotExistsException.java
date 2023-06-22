package com.tutelary.common.exception;

import com.tutelary.common.constants.ServerTaskResponseCode;

public class InstanceNotExistsException extends BaseException {
    public InstanceNotExistsException(String instanceId) {
        super(ServerTaskResponseCode.INSTANCE_NOT_FOUND, instanceId);
    }
}
