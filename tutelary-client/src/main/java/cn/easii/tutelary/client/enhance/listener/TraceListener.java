package cn.easii.tutelary.client.enhance.listener;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.message.command.domain.TraceNode;
import cn.easii.tutelary.message.command.result.TraceResponse;
import cn.easii.tutelary.client.command.domain.TraceEntity;
import cn.easii.tutelary.client.enhance.callback.RCallback;
import cn.hutool.core.exceptions.ExceptionUtil;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class TraceListener extends TraceAdviceListenerAdapter {

    private static final Log LOGGER = LogFactory.get(TraceListener.class);

    private final RCallback<TraceNode> rCallback;

    private final ThreadLocal<TraceEntity> traceEntityThreadLocal = new ThreadLocal<>();

    public TraceListener(RCallback<TraceNode> rCallback) {
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
            TraceNode root = traceEntity.getRoot();
            if (root.getClassName().equals(clazz.getName()) && root.getMethodName().equals(methodName)) {
                traceEntity.end();
                finish(traceEntity);
            }
        });
    }

    @Override
    public void afterThrowing(Class<?> clazz,
        String methodName,
        String methodDesc,
        Object target,
        Object[] args,
        Throwable throwable) throws Throwable {
        LOGGER.debug("method : {} ----- afterThrowing : {}", methodName, ExceptionUtil.stacktraceToString(throwable));
        Optional.ofNullable(traceEntityThreadLocal.get()).ifPresent(traceEntity -> {
            TraceNode root = traceEntity.getRoot();
            if (root.getClassName().equals(clazz.getName()) && root.getMethodName().equals(methodName)) {
                traceEntity.end(throwable);
                finish(traceEntity);
            }
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
            rCallback.callback(traceEntity.getRoot());
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
            .ifPresent(traceEntity -> traceEntity.start(tracingClassName.replaceAll("/", "."), tracingMethodName,
                tracingLineNumber));
    }

    @Override
    public void invokeThrowTracing(ClassLoader classLoader,
        String tracingClassName,
        String tracingMethodName,
        String tracingMethodDesc,
        int tracingLineNumber,
        Throwable throwable) throws Throwable {
        LOGGER.debug("method : {} ----- invokeThrowTracing", tracingMethodName);
        Optional.ofNullable(traceEntityThreadLocal.get()).ifPresent(traceEntity -> traceEntity.end(throwable));
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
