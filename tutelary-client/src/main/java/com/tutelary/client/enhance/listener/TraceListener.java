package com.tutelary.client.enhance.listener;

import com.tutelary.client.command.domain.TraceEntity;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.command.domain.TraceNode;
import com.tutelary.message.command.result.TraceResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class TraceListener extends TraceAdviceListenerAdapter {

    private static final Log LOGGER = LogFactory.get(TraceListener.class);

    private final RCallback<TraceResponse> rCallback;

    private final ThreadLocal<TraceEntity> traceEntityThreadLocal = new ThreadLocal<>();

    public TraceListener(RCallback<TraceResponse> rCallback) {
        this.rCallback = rCallback;
    }

    @Override
    public void before(Class<?> clazz,
        String methodName,
        String methodDesc,
        Object target,
        Object[] args) throws Throwable {
        /*
            获取线程上下文节点信息
         */
        TraceEntity traceEntity = traceEntityThreadLocal.get();
        if (traceEntity == null) {
            TraceNode root = TraceNode.newNode(clazz.getName(), methodName, -1);
            traceEntity = new TraceEntity(root);
            traceEntityThreadLocal.set(traceEntity);
        }
    }

    @Override
    public void afterReturning(Class<?> clazz,
        String methodName,
        String methodDesc,
        Object target,
        Object[] args,
        Object returnObject) throws Throwable {
        LOGGER.debug("method : {} ----- afterReturning", methodName);
        Optional.ofNullable(traceEntityThreadLocal.get()).ifPresent(traceEntity -> {
            traceEntity.end();
            finish(traceEntity);
        });
    }

    @Override
    public void afterThrowing(Class<?> clazz,
        String methodName,
        String methodDesc,
        Object target,
        Object[] args,
        Throwable throwable) throws Throwable {
        LOGGER.debug("method : {} ----- afterThrowing", methodName);
        Optional.ofNullable(traceEntityThreadLocal.get()).ifPresent(traceEntity -> {
            traceEntity.end();
            finish(traceEntity);
        });
    }

    private void finish(TraceEntity traceEntity) {
        if (traceEntity.isFinish()) {
            traceEntityThreadLocal.remove();
            for (TraceNode node : traceEntity.getTraceResult().getNode().getChildren()) {
                LOGGER.debug(
                    "method : {}，cost ms : {}", node.getMethodName(),
                    TimeUnit.NANOSECONDS.toMillis(node.getEndTimestamp() - node.getBeginTimestamp())
                );
            }
            rCallback.callback(traceEntity.getTraceResult());
        }
    }

    @Override
    public void invokeBeforeTracing(ClassLoader classLoader,
        String tracingClassName,
        String tracingMethodName,
        String tracingMethodDesc,
        int tracingLineNumber) throws Throwable {
        LOGGER.debug("method : {} ----- invokeBeforeTracing", tracingMethodName);
        Optional.ofNullable(traceEntityThreadLocal.get())
            .ifPresent(traceEntity -> traceEntity.start(tracingClassName, tracingMethodName, tracingLineNumber));
    }

    @Override
    public void invokeThrowTracing(ClassLoader classLoader,
        String tracingClassName,
        String tracingMethodName,
        String tracingMethodDesc,
        int tracingLineNumber) throws Throwable {
        LOGGER.debug("method : {} ----- invokeThrowTracing", tracingMethodName);
        Optional.ofNullable(traceEntityThreadLocal.get()).ifPresent(TraceEntity::end);
    }

    @Override
    public void invokeAfterTracing(ClassLoader classLoader,
        String tracingClassName,
        String tracingMethodName,
        String tracingMethodDesc,
        int tracingLineNumber) throws Throwable {
        LOGGER.debug("method : {} ----- invokeAfterTracing", tracingMethodName);
        Optional.ofNullable(traceEntityThreadLocal.get()).ifPresent(TraceEntity::end);
    }
}
