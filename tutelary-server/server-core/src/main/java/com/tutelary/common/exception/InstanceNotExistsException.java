package com.tutelary.common.exception;

public class InstanceNotExistsException extends BaseException {
    public InstanceNotExistsException(String instanceId) {
        super(instanceId + " not exists");
    }
}
