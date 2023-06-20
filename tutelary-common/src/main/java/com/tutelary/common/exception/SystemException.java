package com.tutelary.common.exception;

import com.tutelary.common.constants.CommonResponseCode;

public class SystemException extends BaseException {
    public SystemException() {
        super(CommonResponseCode.SERVER_ERROR);
    }
}
