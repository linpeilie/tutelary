package com.tutelary.client.enhance.listener;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AdviceListenerAdapter implements AdviceListener {

    private static final AtomicLong ATOMIC = new AtomicLong(0);

    private final Long id;

    public AdviceListenerAdapter() {
        this.id = ATOMIC.incrementAndGet();
    }

    @Override
    public Long id() {
        return id;
    }

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
