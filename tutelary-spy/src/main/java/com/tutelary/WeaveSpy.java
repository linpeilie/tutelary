package com.tutelary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WeaveSpy {

    private static volatile Spy SPY = NopSpy.getInstance();

    public static void installSpy(Spy spy) {
        SPY = spy;
    }



    public static void atEnter(Class<?> clazz, String methodInfo, Object target, Object[] args) {
        SPY.atEnter(clazz, methodInfo, target, args);
    }

    public static void atExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Object returnObj) {
        SPY.atExit(clazz, methodInfo, target, args, returnObj);
    }

    public static void atExceptionExit(Class<?> clazz, String methodInfo, Object target,
                                       Object[] args, Throwable throwable) {
        SPY.atExceptionExit(clazz, methodInfo, target, args, throwable);
    }

    public static void atBeforeInvoke(Class<?> clazz, String invokeInfo, Object target) {
        SPY.atBeforeInvoke(clazz, invokeInfo, target);
    }

    public static void atAfterInvoke(Class<?> clazz, String invokeInfo, Object target) {
        SPY.atAfterInvoke(clazz, invokeInfo, target);
    }

    public static void atInvokeException(Class<?> clazz, String invokeInfo, Object target, Throwable throwable) {
        SPY.atInvokeException(clazz, invokeInfo, target, throwable);
    }

}
