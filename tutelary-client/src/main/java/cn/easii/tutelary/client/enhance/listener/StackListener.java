package cn.easii.tutelary.client.enhance.listener;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.message.command.domain.StackTraceNode;
import cn.easii.tutelary.message.command.result.StackResponse;
import cn.hutool.core.collection.CollectionUtil;
import cn.easii.tutelary.client.enhance.callback.RCallback;
import java.tutelary.WeaveSpy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class StackListener extends AdviceListenerAdapter {

    private static final Log LOG = LogFactory.get(StackListener.class);

    private final RCallback<StackResponse> rCallback;

    private final ThreadLocal<Queue<StackResponse>> stackResultThreadLocal = new ThreadLocal<>();

    public StackListener(RCallback<StackResponse> rCallback) {
        this.rCallback = rCallback;
    }

    @Override
    public void before(Class<?> clazz,
        String methodName,
        String methodDesc,
        Object target,
        Object[] args) throws Throwable {
        Queue<StackResponse> stackQueue = stackResultThreadLocal.get();
        if (stackQueue == null) {
            stackQueue = new ConcurrentLinkedQueue<>();
        }
        StackResponse stackResult = new StackResponse();
        stackResult.setStartTimestamp(System.nanoTime());
        stackQueue.offer(stackResult);
        stackResultThreadLocal.set(stackQueue);
    }

    @Override
    public void afterReturning(Class<?> clazz,
        String methodName,
        String methodDesc,
        Object target,
        Object[] args,
        Object returnObject) throws Throwable {
        finish();
    }

    @Override
    public void afterThrowing(Class<?> clazz,
        String methodName,
        String methodDesc,
        Object target,
        Object[] args,
        Throwable throwable) throws Throwable {
        finish();
    }

    private void finish() {
        Queue<StackResponse> queue = stackResultThreadLocal.get();
        if (queue == null) {
            return;
        }
        StackResponse stackResult = queue.poll();
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
        CollectionUtil.reverse(stackTraceNodes);
        final int weaspyIndex = CollectionUtil.indexOf(stackTraceNodes,
            node -> node.getDeclaringClass().contentEquals(WeaveSpy.class.getName()));
        if (weaspyIndex > -1) {
            stackTraceNodes = stackTraceNodes.subList(0, weaspyIndex);
        }
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
