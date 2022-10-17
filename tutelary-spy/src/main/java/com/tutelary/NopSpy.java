package com.tutelary;

public class NopSpy implements Spy {

    private static final NopSpy NOP_SPY = new NopSpy();

    private NopSpy() {}

    public static NopSpy getInstance() {
        return NOP_SPY;
    }

    @Override
    public void atEnter(Class<?> clazz, String methodInfo, Object target, Object[] args) {
        System.out.println("nop");
    }

    @Override
    public void atExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Object returnObj) {
        System.out.println("nop");
    }

    @Override
    public void atExceptionExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Throwable throwable) {
        System.out.println("nop");
    }

    @Override
    public void atBeforeInvoke(Class<?> clazz, String invokeInfo, Object target) {
        System.out.println("nop");
    }

    @Override
    public void atAfterInvoke(Class<?> clazz, String invokeInfo, Object target) {
        System.out.println("nop");
    }

    @Override
    public void atInvokeException(Class<?> clazz, String invokeInfo, Object target, Throwable throwable) {
        System.out.println("nop");
    }
}
