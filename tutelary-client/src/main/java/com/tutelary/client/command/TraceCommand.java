package com.tutelary.client.command;

import cn.hutool.core.collection.CollectionUtil;
import com.tutelary.client.enhance.listener.AdviceListener;
import com.tutelary.client.enhance.listener.TraceListener;

import java.lang.instrument.Instrumentation;
import java.util.List;

public class TraceCommand extends AbstractEnhanceCommand {
    public TraceCommand(Instrumentation inst) {
        super(inst);
    }

    @Override
    protected AdviceListener getListener() {
        return new TraceListener();
    }

    @Override
    protected String targetClass() {
        return "com.tutelary.example.tutelaryexample.GameController";
    }

    @Override
    protected List<String> targetMethods() {
        return CollectionUtil.newArrayList("hello");
    }
}
