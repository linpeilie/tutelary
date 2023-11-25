package cn.easii.tutelary.client.command.trace;

import cn.easii.tutelary.client.enhance.callback.RCallback;
import cn.easii.tutelary.client.enhance.listener.AdviceListener;
import cn.easii.tutelary.client.enhance.listener.TraceListener;
import cn.easii.tutelary.message.command.param.TraceRequest;
import cn.easii.tutelary.message.command.result.TraceResponse;
import cn.easii.tutelary.client.command.AbstractEnhanceCommand;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TraceCommand extends AbstractEnhanceCommand<TraceRequest, TraceResponse> {

    private final TraceRequest param;

    private final AtomicInteger execCnt = new AtomicInteger(0);

    public TraceCommand(Instrumentation inst, TraceRequest param) {
        super(inst);
        this.param = param;
    }

    @Override
    protected AdviceListener getListener() {
        return new TraceListener(new RCallback<>(traceResult -> {
            // 耗时
            if (param.getCost() > 0 && traceResult.totalTimeSpent() < param.getCost()) {
                return;
            }
            // 执行次数比较
            int execCnt = this.execCnt.incrementAndGet();
            // 如果等于的话
            if (execCnt <= param.getTimes()) {
                // 执行逻辑
                callbackResult(traceResult);
            }
            // 任务完成
            if (execCnt == param.getTimes()) {
                // 完成逻辑
                complete();
            }
        }));
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
