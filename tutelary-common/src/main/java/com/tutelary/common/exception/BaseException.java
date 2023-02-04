package com.tutelary.common.exception;

public abstract class BaseException extends RuntimeException {

    public BaseException(String errorMessage) {
        super(errorMessage);
    }

    public BaseException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public String getErrorMessage() {
        return getMessage();
    }
}
