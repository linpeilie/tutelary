package com.tutelary.common.exception;

import com.tutelary.common.constants.CommonResponseCode;

public class IllegalArgumentException extends BusinessException {
    public IllegalArgumentException(String errorMessage) {
        super(CommonResponseCode.ILLEGAL_ARGUMENT, errorMessage);
    }
}
