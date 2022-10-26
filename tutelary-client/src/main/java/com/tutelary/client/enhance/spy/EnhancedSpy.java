package com.tutelary.client.enhance.spy;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import java.tutelary.Spy;

public class EnhancedSpy implements Spy {

    private static final Log LOG = LogFactory.get();

    @Override
    public void atEnter(Class<?> clazz, String methodInfo, Object target, Object[] args) {
        LOG.info("atEnter ----- class : {}, methodInfo : {}, target : {}, args : {}",
                clazz, methodInfo, target, args);
    }

    @Override
    public void atExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Object returnObj) {
        LOG.info("atExit ----- class : {}, methodInfo : {}, target : {}, args : {}, returnObj : {}",
                clazz, methodInfo, target, args, returnObj);
    }

    @Override
    public void atExceptionExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Throwable throwable) {
        LOG.info("atExceptionExit ----- class : {}, methodInfo : {}, target : {}, args : {}, throwable : {}",
                clazz, methodInfo, target, args, throwable);
    }

    @Override
    public void atBeforeInvoke(Class<?> clazz, String invokeInfo, Object target) {
        LOG.info("atBeforeInvoke ----- class : {}, invokeInfo : {}, target : {}",
                clazz, invokeInfo, target);
    }

    @Override
    public void atAfterInvoke(Class<?> clazz, String invokeInfo, Object target) {
        LOG.info("atAfterInvoke ----- class : {}, invokeInfo : {}, target : {}",
                clazz, invokeInfo, target);
    }

    @Override
    public void atInvokeException(Class<?> clazz, String invokeInfo, Object target, Throwable throwable) {
        LOG.info("atAfterInvoke ----- class : {}, invokeInfo : {}, target : {}, throwable : {}",
                clazz, invokeInfo, target, throwable);
    }
}
