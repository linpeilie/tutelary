package cn.easii.tutelary.client.exception;

import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.client.constants.ClientTaskResponseCode;

public class ClassLoaderNotFoundException extends BaseException {

    public ClassLoaderNotFoundException(String classLoaderHash) {
        super(ClientTaskResponseCode.CLASSLOADER_NOT_FOUND, classLoaderHash);
    }

}
