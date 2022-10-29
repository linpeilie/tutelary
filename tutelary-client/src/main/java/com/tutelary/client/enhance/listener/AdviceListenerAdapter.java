package com.tutelary.client.enhance.listener;

public abstract class AdviceListenerAdapter implements AdviceListener {

    @Override
    public void create() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void before(Class<?> clazz, String methodName, String methodDesc, Object target, Object[] args) throws Throwable {

    }

    @Override
    public void afterReturning(Class<?> clazz, String methodName, String methodDesc, Object target, Object[] args, Object returnObject) throws Throwable {

    }

    @Override
    public void afterThrowing(Class<?> clazz, String methodName, String methodDesc, Object target, Object[] args, Throwable throwable) throws Throwable {

    }
}
