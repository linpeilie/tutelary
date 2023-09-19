package com.tutelary.client.exception;

import com.tutelary.client.constants.ClientTaskResponseCode;
import com.tutelary.common.exception.BaseException;

public class ClassLoaderNotFoundException extends BaseException {

    public ClassLoaderNotFoundException(String classLoaderHash) {
        super(ClientTaskResponseCode.CLASSLOADER_NOT_FOUND, classLoaderHash);
    }

}
