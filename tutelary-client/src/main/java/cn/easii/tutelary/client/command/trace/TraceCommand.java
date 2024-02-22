package cn.easii.tutelary.client.command.trace;

import cn.easii.tutelary.client.enhance.callback.RCallback;
import cn.easii.tutelary.client.enhance.listener.AdviceListener;
import cn.easii.tutelary.client.enhance.listener.TraceListener;
import cn.easii.tutelary.message.command.domain.TraceNode;
import cn.easii.tutelary.message.command.param.TraceRequest;
import cn.easii.tutelary.message.command.result.TraceResponse;
import cn.easii.tutelary.client.command.AbstractEnhanceCommand;
import cn.hutool.core.collection.CollectionUtil;
import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.util.CollectionUtils;

public class TraceCommand extends AbstractEnhanceCommand<TraceRequest, TraceResponse> {

    private final TraceRequest param;

    private final Map<String, AtomicInteger> methodExecCng = new HashMap<>();

    private String methodExecKey(String className, String methodName) {
        return className + "___" + methodName;
    }

    public TraceCommand(Instrumentation inst, TraceRequest param) {
        super(inst);
        this.param = param;
        param.getMethodNames()
            .forEach(methodName ->
                methodExecCng.put(
                    methodExecKey(param.getQualifiedClassName(), methodName), new AtomicInteger(0)
                )
            );
    }

    @Override
    protected AdviceListener getListener() {
        return new TraceListener(new RCallback<>(this::finish));
    }

    private void finish(TraceNode traceNode) {
        finish1(traceNode);
        List<TraceNode> children = traceNode.getChildren();
        if (CollectionUtil.isNotEmpty(children)) {
            for (TraceNode child : children) {
                finish(child);
            }
        }
    }

    private void finish1(TraceNode node) {
        // 耗时
        if (param.getCost() >= 0 && node.totalTimeSpent() < param.getCost()) {
            return;
        }
        String key = methodExecKey(node.getClassName(), node.getMethodName());
        if (!methodExecCng.containsKey(key)) {
            return;
        }
        AtomicInteger execCnt = methodExecCng.get(key);
        // 执行次数比较
        int cnt = execCnt.incrementAndGet();
        // 如果等于的话
        if (cnt <= param.getTimes()) {
            // 执行逻辑
            callbackResult(TraceResponse.newInstance(node));
        }
        // 任务完成
        if (cnt >= param.getTimes() && isCompletes()) {
            // 完成逻辑
            complete();
        }
    }

    private boolean isCompletes() {
        return methodExecCng.values()
            .stream()
            .allMatch(execCnt -> execCnt.get() >= param.getTimes());
    }

    @Override
    protected String targetClass() {
        return param.getQualifiedClassName();
    }

    @Override
    protected List<String> targetMethods() {
        return param.getMethodNames();
    }
}
