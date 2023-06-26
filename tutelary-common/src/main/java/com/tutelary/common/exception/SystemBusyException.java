package com.tutelary.common.exception;

import com.tutelary.common.constants.CommonResponseCode;

public class SystemBusyException extends BaseException {
    public SystemBusyException() {
        super(CommonResponseCode.SERVER_BUSY);
    }
}
