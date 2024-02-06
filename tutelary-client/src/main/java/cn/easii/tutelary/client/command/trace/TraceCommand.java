package cn.easii.tutelary.client.command.trace;

import cn.easii.tutelary.client.enhance.callback.RCallback;
import cn.easii.tutelary.client.enhance.listener.AdviceListener;
import cn.easii.tutelary.client.enhance.listener.TraceListener;
import cn.easii.tutelary.message.command.param.TraceRequest;
import cn.easii.tutelary.message.command.result.TraceResponse;
import cn.easii.tutelary.client.command.AbstractEnhanceCommand;
import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TraceCommand extends AbstractEnhanceCommand<TraceRequest, TraceResponse> {

    private final TraceRequest param;

    private final Map<String, AtomicInteger> methodExecCng = new HashMap<>();

    public TraceCommand(Instrumentation inst, TraceRequest param) {
        super(inst);
        this.param = param;
        param.getMethodNames()
            .forEach(methodName -> methodExecCng.put(methodName, new AtomicInteger(0)));
    }

    @Override
    protected AdviceListener getListener() {
        return new TraceListener(new RCallback<>(traceResult -> {
            // 耗时
            if (param.getCost() > 0 && traceResult.totalTimeSpent() < param.getCost()) {
                return;
            }
            AtomicInteger execCnt = methodExecCng.get(traceResult.getNode().getMethodName());
            // 执行次数比较
            int cnt = execCnt.incrementAndGet();
            // 如果等于的话
            if (cnt <= param.getTimes()) {
                // 执行逻辑
                callbackResult(traceResult);
            }
            // 任务完成
            if (cnt >= param.getTimes() && isCompletes()) {
                // 完成逻辑
                complete();
            }
        }));
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
