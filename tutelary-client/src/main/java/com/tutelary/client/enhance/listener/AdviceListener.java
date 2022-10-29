package com.tutelary.client.enhance.listener;

public interface AdviceListener {

    void create();

    void destroy();

    void before(Class<?> clazz,
                String methodName,
                String methodDesc,
                Object target,
                Object[] args) throws Throwable;

    void afterReturning(Class<?> clazz,
                        String methodName,
                        String methodDesc,
                        Object target,
                        Object[] args,
                        Object returnObject) throws Throwable;

    void afterThrowing(Class<?> clazz,
                       String methodName,
                       String methodDesc,
                       Object target,
                       Object[] args,
                       Throwable throwable) throws Throwable;

}
