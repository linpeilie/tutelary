package cn.easii.tutelary.client.command.stack;

import cn.easii.tutelary.client.enhance.callback.RCallback;
import cn.easii.tutelary.client.enhance.listener.AdviceListener;
import cn.easii.tutelary.client.enhance.listener.StackListener;
import cn.easii.tutelary.message.command.param.StackRequest;
import cn.easii.tutelary.message.command.result.StackResponse;
import cn.easii.tutelary.client.command.AbstractEnhanceCommand;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StackCommand extends AbstractEnhanceCommand<StackRequest, StackResponse> {

    private final StackRequest param;

    private final AtomicInteger execCnt = new AtomicInteger(0);

    public StackCommand(Instrumentation inst, StackRequest param) {
        super(inst);
        this.param = param;
    }

    @Override
    protected AdviceListener getListener() {
        return new StackListener(new RCallback<>(stackResult -> {
            // 次数
            int execCnt = this.execCnt.incrementAndGet();
            if (execCnt <= param.getTimes()) {
                callbackResult(stackResult);
            }
            if (execCnt == param.getTimes()) {
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
