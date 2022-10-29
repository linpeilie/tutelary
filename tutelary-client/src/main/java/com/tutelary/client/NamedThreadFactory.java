package com.tutelary.client;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger THREAD_FACTORY_SEQ = new AtomicInteger(0);

    private final AtomicInteger threadSeq = new AtomicInteger(0);

    private final String namePrefix;

    public NamedThreadFactory(String name) {
        namePrefix = "Tutelary-" + THREAD_FACTORY_SEQ.incrementAndGet() + "-" + name + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, namePrefix + threadSeq.getAndIncrement());
        thread.setDaemon(true);
        return thread;
    }
}
