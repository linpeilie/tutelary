package cn.easii.tutelary.client.exception;

import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.client.constants.ClientTaskResponseCode;

public class ClassNotFoundException extends BaseException {

    public ClassNotFoundException(String classLoader, String className) {
        super(ClientTaskResponseCode.CLASS_NOT_FOUND, className, classLoader);
    }

}
