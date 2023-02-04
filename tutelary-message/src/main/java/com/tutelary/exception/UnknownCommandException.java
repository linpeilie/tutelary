package com.tutelary.exception;

import com.tutelary.common.exception.BaseException;

public class UnknownCommandException extends BaseException {

    private byte cmd;

    public UnknownCommandException(byte cmd) {
        super("Unknown Command " + cmd);
    }
}
