package com.tutelary.common.exception;

import com.tutelary.common.constants.ResponseCode;

public class BusinessException extends BaseException {
    public BusinessException(ResponseCode responseCode, Object... codeMessageArgs) {
        super(responseCode, codeMessageArgs);
    }
}
