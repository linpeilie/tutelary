package com.tutelary;

public interface Spy {

    void atEnter(Class<?> clazz, String methodInfo, Object target, Object[] args);

    void atExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Object returnObj);

    void atExceptionExit(Class<?> clazz, String methodInfo, Object target,
                                       Object[] args, Throwable throwable);

    void atBeforeInvoke(Class<?> clazz, String invokeInfo, Object target);

    void atAfterInvoke(Class<?> clazz, String invokeInfo, Object target);

    void atInvokeException(Class<?> clazz, String invokeInfo, Object target, Throwable throwable);

}
