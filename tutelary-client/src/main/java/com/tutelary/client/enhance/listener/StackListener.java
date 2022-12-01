package com.tutelary.client.enhance.listener;

import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.command.domain.StackTraceNode;
import com.tutelary.message.command.result.StackResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class StackListener extends AdviceListenerAdapter {

    private static final Log LOG = LogFactory.get(StackListener.class);

    private final RCallback<StackResult> rCallback;

    private final ThreadLocal<Queue<StackResult>> stackResultThreadLocal = new ThreadLocal<>();

    public StackListener(RCallback<StackResult> rCallback) {
        this.rCallback = rCallback;
    }

    @Override
    public void before(Class<?> clazz, String methodName, String methodDesc, Object target, Object[] args) throws Throwable {
        Queue<StackResult> stackQueue = stackResultThreadLocal.get();
        if (stackQueue == null) {
            stackQueue = new ConcurrentLinkedQueue<>();
        }
        StackResult stackResult = new StackResult();
        stackResult.setStartTimestamp(System.nanoTime());
        stackQueue.offer(stackResult);
        stackResultThreadLocal.set(stackQueue);
    }

    @Override
    public void afterReturning(Class<?> clazz, String methodName, String methodDesc, Object target, Object[] args, Object returnObject) throws Throwable {
        finish();
    }

    @Override
    public void afterThrowing(Class<?> clazz, String methodName, String methodDesc, Object target, Object[] args, Throwable throwable) throws Throwable {
        finish();
    }

    private void finish() {
        Queue<StackResult> queue = stackResultThreadLocal.get();
        if (queue == null) {
            return;
        }
        StackResult stackResult = queue.poll();
        if (stackResult == null) {
            return;
        }
        if (queue.isEmpty()) {
            stackResultThreadLocal.remove();
        }
        stackResult.setEndTimestamp(System.nanoTime());
        Thread thread = Thread.currentThread();
        stackResult.setThreadId(thread.getId());
        stackResult.setThreadName(thread.getName());
        stackResult.setDaemon(thread.isDaemon());
        stackResult.setClassloader(thread.getContextClassLoader().getClass().getName());
        StackTraceElement[] stackTrace = thread.getStackTrace();
        List<StackTraceNode> stackTraceNodes = Arrays.stream(stackTrace)
                .map(element -> {
                    StackTraceNode stackTraceNode = new StackTraceNode();
                    stackTraceNode.setDeclaringClass(element.getClassName());
                    stackTraceNode.setMethodName(element.getMethodName());
                    stackTraceNode.setLineNumber(element.getLineNumber());
                    return stackTraceNode;
                }).collect(Collectors.toList());
        stackResult.setStackTraceNodeList(stackTraceNodes);
        rCallback.callback(stackResult);
    }

    private List<StackTraceElement> filterStackTrace(String targetClassName,
                                                     String targetMethodName,
                                                     StackTraceElement[] stackTraceElements) {
        List<StackTraceElement> list = new ArrayList<>();
        int startIndex = 0;
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getClassName().equals(targetClassName)
                    && stackTraceElement.getMethodName().equals(targetMethodName)) {
                break;
            }
            startIndex++;
        }
        return list;
    }

}
