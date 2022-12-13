package com.tutelary.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutors {

    private static final ScheduledExecutorService EXECUTORS
            = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("scheduled-executors"));

    public static void destroy() {
        EXECUTORS.shutdownNow();
    }

    public static ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit timeUnit) {
        return EXECUTORS.schedule(command, delay, timeUnit);
    }

    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                            long initialDelay,
                                                            long delay,
                                                            TimeUnit unit) {
        return EXECUTORS.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

}
