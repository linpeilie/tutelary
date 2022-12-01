package com.tutelary.client.command.stack;

import com.tutelary.client.command.AbstractEnhanceCommand;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.client.enhance.listener.AdviceListener;
import com.tutelary.client.enhance.listener.StackListener;
import com.tutelary.message.command.param.StackParam;
import com.tutelary.message.command.result.StackResult;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StackCommand extends AbstractEnhanceCommand<StackParam, StackResult> {

    private final StackParam param;

    private final AtomicInteger execCnt = new AtomicInteger(0);

    public StackCommand(Instrumentation inst, StackParam param) {
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
        return param.getClassName();
    }

    @Override
    protected List<String> targetMethods() {
        return param.getMethodNames();
    }
}
