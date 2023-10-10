package com.tutelary.client.exception;

import com.tutelary.client.constants.ClientTaskResponseCode;
import com.tutelary.common.exception.BaseException;

public class ClassNotFoundException extends BaseException {

    public ClassNotFoundException(String classLoader, String className) {
        super(ClientTaskResponseCode.CLASS_NOT_FOUND, className, classLoader);
    }

}
