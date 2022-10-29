package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class BaseMethod implements Serializable {

    private String classLoader;

    private String className;

    private String methodName;

    private String methodDesc;

    public BaseMethod() {
    }

    public static BaseMethod build(ClassLoader classLoader, String className, String methodName, String methodDesc) {
        BaseMethod baseMethod = new BaseMethod();
        baseMethod.classLoader = classLoader == null ? "BootstrapClassLoader" : classLoader.toString();
        baseMethod.className = className;
        baseMethod.methodName = methodName;
        baseMethod.methodDesc = methodDesc;
        return baseMethod;
    }

}
