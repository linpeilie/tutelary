package com.tutelary.common.exception;

public class BaseException extends RuntimeException {

    private String errorMessage;

    public BaseException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
