package com.tutelary.client.core.perf;

import com.alibaba.bytekit.utils.JavaVersionUtils;
import com.google.common.collect.ImmutableMap;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.stream.Collectors;
import sun.management.counter.Counter;
import sun.management.counter.perf.PerfInstrumentation;

public class PerfData {

    private static final Log LOG = LogFactory.get(PerfData.class);

    private static final Object PERF_INSTANCE;

    private static final Method ATTACH_METHOD;

    static {
        try {
            Class<?> perfClass;
            if (JavaVersionUtils.isLessThanJava9()) {
                perfClass = Class.forName("sun.misc.Perf");
            } else {
                perfClass = Class.forName("jdk.internal.perf.Perf");
            }
            Method getPerfMethod = perfClass.getMethod("getPerf");
            PERF_INSTANCE = getPerfMethod.invoke(null);
            ATTACH_METHOD = perfClass.getMethod("attach", int.class, String.class);
        } catch (ClassNotFoundException e) {
            LOG.error("reflect Perf class error", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            LOG.error("pref instance init error", e);
            throw new RuntimeException(e);
        }
    }

    private final PerfInstrumentation instr;

    private final ImmutableMap<String, Counter> counters;

    private PerfData(int pid) throws InvocationTargetException, IllegalAccessException {
        ByteBuffer byteBuffer = (ByteBuffer) ATTACH_METHOD.invoke(PERF_INSTANCE, pid, "r");
        instr = new PerfInstrumentation(byteBuffer);
        counters = buildAllCounters();
    }

    public static PerfData connect(int pid) {
        try {
            return new PerfData(pid);
        } catch (ThreadDeath | OutOfMemoryError e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException("Cannot perf data for process " + pid);
        }
    }

    private ImmutableMap<String, Counter> buildAllCounters() {
        Map<String, Counter> counterMap = instr.getAllCounters()
            .stream()
            .collect(Collectors.toMap(Counter::getName, counter -> counter));
        return ImmutableMap.copyOf(counterMap);
    }

    public Map<String, Counter> getAllCounters() {
        return counters;
    }

    public Counter findCounter(String counterName) {
        return counters.get(counterName);
    }

}
