package com.tutelary.client.command.stack;

import com.tutelary.client.command.AbstractEnhanceCommand;
import com.tutelary.client.enhance.callback.RCallback;
import com.tutelary.client.enhance.listener.AdviceListener;
import com.tutelary.client.enhance.listener.StackListener;
import com.tutelary.message.command.param.StackRequest;
import com.tutelary.message.command.result.StackResponse;

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
        return param.getClassName();
    }

    @Override
    protected List<String> targetMethods() {
        return param.getMethodNames();
    }
}
