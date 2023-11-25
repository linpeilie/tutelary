package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.ServerTaskResponseCode;

public class InstanceNotExistsException extends BaseException {
    public InstanceNotExistsException(String instanceId) {
        super(ServerTaskResponseCode.INSTANCE_NOT_FOUND, instanceId);
    }
}
