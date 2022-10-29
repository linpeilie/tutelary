package com.tutelary.common.exception;

public class SessionClosedException extends BaseException {

    public SessionClosedException(String sessionId) {
        super(sessionId + " connection closed!");
    }
}
