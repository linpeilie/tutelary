package com.tutelary.client.enhance.listener;

public abstract class TraceAdviceListenerAdapter extends AdviceListenerAdapter implements InvokeTraceListener {
    @Override
    public void invokeBeforeTracing(ClassLoader classLoader, String tracingClassName, String tracingMethodName, String tracingMethodDesc, int tracingLineNumber) throws Throwable {

    }

    @Override
    public void invokeThrowTracing(ClassLoader classLoader, String tracingClassName, String tracingMethodName, String tracingMethodDesc, int tracingLineNumber) throws Throwable {

    }

    @Override
    public void invokeAfterTracing(ClassLoader classLoader, String tracingClassName, String tracingMethodName, String tracingMethodDesc, int tracingLineNumber) throws Throwable {

    }
}
