package com.tutelary.common.exception;

import com.tutelary.common.constants.ResponseCode;

public abstract class BaseException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public BaseException(ResponseCode responseCode, Object... codeMessageArgs) {
        super(String.format(responseCode.getMessage(), codeMessageArgs));
        this.errorCode = responseCode.getCode();
        if (codeMessageArgs != null && codeMessageArgs.length > 0) {
            this.errorMessage = String.format(responseCode.getMessage(), codeMessageArgs);
        } else {
            this.errorMessage = responseCode.getMessage();
        }
    }
}
