package cn.easii.tutelary.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger THREAD_FACTORY_SEQ = new AtomicInteger(0);

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup( "tutelary");

    static {
        THREAD_GROUP.setDaemon(true);
    }

    private final AtomicInteger threadSeq = new AtomicInteger(0);
    private final String namePrefix;

    public NamedThreadFactory(String name) {
        namePrefix = "Tutelary-" + THREAD_FACTORY_SEQ.incrementAndGet() + "-" + name + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r, namePrefix + threadSeq.getAndIncrement());
        thread.setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler());
        return thread;
    }
}
