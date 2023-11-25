package cn.easii.tutelary.client.enhance.spy;

import cn.easii.tutelary.client.enhance.listener.AdviceListener;
import cn.easii.tutelary.client.enhance.listener.AdviceListenerManager;
import cn.easii.tutelary.client.enhance.listener.InvokeTraceListener;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.util.internal.ThrowableUtil;
import java.tutelary.Spy;
import java.util.List;

public class EnhancedSpy implements Spy {

    private static final Log LOGGER = LogFactory.get(EnhancedSpy.class);

    @Override
    public void atEnter(Class<?> clazz, String methodInfo, Object target, Object[] args) {
        ClassLoader classLoader = clazz.getClassLoader();
        String[] info = StrUtil.splitToArray(methodInfo, '|');
        String methodName = info[0];
        String methodDesc = info[1];
        List<AdviceListener> adviceListeners = AdviceListenerManager.queryAdviceListeners(
            classLoader, clazz.getName(), methodName, methodDesc);
        if (CollectionUtil.isNotEmpty(adviceListeners)) {
            adviceListeners.forEach(adviceListener -> {
                try {
                    LOGGER.debug(
                        "atEnter ----- adviceListener : {} ----- class : {}, methodName : {}, methodDesc : {}, target : {}, args : {}",
                        adviceListener, clazz, methodName, methodDesc, target, args
                    );
                    adviceListener.before(clazz, methodName, methodDesc, target, args);
                } catch (Throwable e) {
                    LOGGER.error("class : {}, method : {}, adviceListener : {}, atEnter error : {}",
                        clazz.getName(), methodName, adviceListener, ThrowableUtil.stackTraceToString(e)
                    );
                }
            });
        }
    }

    @Override
    public void atExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Object returnObj) {
        ClassLoader classLoader = clazz.getClassLoader();
        String[] info = StrUtil.splitToArray(methodInfo, '|');
        String methodName = info[0];
        String methodDesc = info[1];
        List<AdviceListener> adviceListeners = AdviceListenerManager.queryAdviceListeners(
            classLoader, clazz.getName(), methodName, methodDesc);
        if (CollectionUtil.isNotEmpty(adviceListeners)) {
            adviceListeners.forEach(adviceListener -> {
                try {
                    LOGGER.debug(
                        "atExit ----- adviceListener : {} ----- class : {}, methodName : {}, methodDesc : {}, target : {}, args : {}, returnObj : {}",
                        adviceListener, clazz, methodName, methodDesc, target, args, returnObj
                    );
                    adviceListener.afterReturning(clazz, methodName, methodDesc, target, args, returnObj);
                } catch (Throwable e) {
                    LOGGER.error("class : {}, method : {}, adviceListener : {}, atExit error : {}",
                        clazz.getName(), methodName, adviceListener, ThrowableUtil.stackTraceToString(e)
                    );
                }
            });
        }
    }

    @Override
    public void atExceptionExit(Class<?> clazz, String methodInfo, Object target, Object[] args, Throwable throwable) {
        ClassLoader classLoader = clazz.getClassLoader();
        String[] info = StrUtil.splitToArray(methodInfo, '|');
        String methodName = info[0];
        String methodDesc = info[1];
        List<AdviceListener> adviceListeners = AdviceListenerManager.queryAdviceListeners(
            classLoader, clazz.getName(), methodName, methodDesc);
        if (CollectionUtil.isNotEmpty(adviceListeners)) {
            adviceListeners.forEach(adviceListener -> {
                try {
                    LOGGER.debug(
                        "atExceptionExit ----- adviceListener : {} ----- class : {}, methodName : {}, methodDesc : {}, target : {}, args : {}, throwable : {}",
                        adviceListener, clazz, methodName, methodDesc, target, args, throwable
                    );
                    adviceListener.afterThrowing(clazz, methodName, methodDesc, target, args, throwable);
                } catch (Throwable e) {
                    LOGGER.error("class : {}, method : {}, adviceListener : {}, atExceptionExit error : {}",
                        clazz.getName(), methodName, adviceListener, ThrowableUtil.stackTraceToString(e)
                    );
                }
            });
        }
    }

    @Override
    public void atBeforeInvoke(Class<?> clazz, String invokeInfo, Object target) {
        ClassLoader classLoader = clazz.getClassLoader();
        String[] info = StrUtil.splitToArray(invokeInfo, '|');
        String owner = info[0];
        String methodName = info[1];
        String methodDesc = info[2];
        List<AdviceListener> adviceListeners = AdviceListenerManager.queryTraceAdviceListeners(
            classLoader, clazz.getName(), owner, methodName, methodDesc);
        if (CollectionUtil.isNotEmpty(adviceListeners)) {
            adviceListeners.forEach(adviceListener -> {
                int line = Integer.parseInt(info[3]);
                try {
                    LOGGER.debug(
                        "invokeBeforeTracing ----- adviceListener : {} ---- classloader : {}, tracingClassName : {}, tracingMethodName : {}, tracingMethodDesc : {}, tracingLineNumber : {}",
                        adviceListener, classLoader.getClass().getName(), owner, methodName, methodDesc, line
                    );
                    ((InvokeTraceListener) adviceListener).invokeBeforeTracing(
                        classLoader, owner, methodName, methodDesc, line);
                } catch (Throwable e) {
                    LOGGER.error("class : {}, method : {}, line : {}, adviceListener : {}, atBeforeInvoke error : {}",
                        clazz.getName(), methodName, line, adviceListener, ThrowableUtil.stackTraceToString(e)
                    );
                }
            });
        }
    }

    @Override
    public void atAfterInvoke(Class<?> clazz, String invokeInfo, Object target) {
        ClassLoader classLoader = clazz.getClassLoader();
        String[] info = StrUtil.splitToArray(invokeInfo, '|');
        String owner = info[0];
        String methodName = info[1];
        String methodDesc = info[2];
        List<AdviceListener> adviceListeners = AdviceListenerManager.queryTraceAdviceListeners(
            classLoader, clazz.getName(), owner, methodName, methodDesc);
        if (CollectionUtil.isNotEmpty(adviceListeners)) {
            adviceListeners.forEach(adviceListener -> {
                int line = Integer.parseInt(info[3]);
                try {
                    LOGGER.debug(
                        "adviceListener : {}, invokeAfterTracing ---- classloader : {}, tracingClassName : {}, tracingMethodName : {}, tracingMethodDesc : {}, tracingLineNumber : {}",
                        adviceListener, classLoader.getClass().getName(), owner, methodName, methodDesc, line
                    );
                    ((InvokeTraceListener) adviceListener).invokeAfterTracing(
                        classLoader, owner, methodName, methodDesc, line);
                } catch (Throwable e) {
                    LOGGER.error("class : {}, method : {}, line : {}, adviceListener : {}, atAfterInvoke error : {}",
                        clazz.getName(), methodName, line, adviceListener, ThrowableUtil.stackTraceToString(e)
                    );
                }
            });
        }
    }

    @Override
    public void atInvokeException(Class<?> clazz, String invokeInfo, Object target, Throwable throwable) {
        ClassLoader classLoader = clazz.getClassLoader();
        String[] info = StrUtil.splitToArray(invokeInfo, '|');
        String owner = info[0];
        String methodName = info[1];
        String methodDesc = info[2];
        List<AdviceListener> adviceListeners = AdviceListenerManager.queryTraceAdviceListeners(
            classLoader, clazz.getName(), owner, methodName, methodDesc);
        if (CollectionUtil.isNotEmpty(adviceListeners)) {
            adviceListeners.forEach(adviceListener -> {
                int line = Integer.parseInt(info[3]);
                try {
                    LOGGER.debug(
                        "adviceListener : {} ----- invokeThrowTracing ---- classloader : {}, tracingClassName : {}, tracingMethodName : {}, tracingMethodDesc : {}, tracingLineNumber : {}",
                        adviceListener, classLoader.getClass().getName(), owner, methodName, methodDesc, line
                    );
                    ((InvokeTraceListener) adviceListener).invokeThrowTracing(
                        classLoader, owner, methodName, methodDesc, line);
                } catch (Throwable e) {
                    LOGGER.error(
                        "class : {}, method : {}, line : {}, adviceListener : {}, atInvokeException error : {}",
                        clazz.getName(), methodName, line, adviceListener, ThrowableUtil.stackTraceToString(e)
                    );
                }
            });
        }
    }
}
