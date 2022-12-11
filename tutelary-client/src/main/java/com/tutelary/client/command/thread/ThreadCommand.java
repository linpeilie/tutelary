package com.tutelary.client.command.thread;

import com.sun.management.ThreadMXBean;
import com.tutelary.client.command.Command;
import com.tutelary.client.command.ManagementFactory;
import com.tutelary.client.converter.ManagementConverter;
import com.tutelary.client.util.MXBeanUtil;
import com.tutelary.client.util.ThreadUtil;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.command.domain.BaseThreadInfo;
import com.tutelary.message.command.param.ThreadListParam;
import com.tutelary.message.command.result.ThreadList;

import java.util.*;

public class ThreadCommand implements Command<ThreadList> {

    private static final Log LOG = LogFactory.get(ThreadCommand.class);

    private final MXBeanUtil mxBeanUtil = new MXBeanUtil();

    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    private final ThreadListParam threadCommandParam;

    public ThreadCommand(ThreadListParam threadCommandParam) {
        this.threadCommandParam = threadCommandParam;
    }

    @Override
    public ThreadList execute() {
        // 获取所有线程
        List<Thread> threads = ThreadUtil.getThreads();
        long[] threadIds = threads.stream().mapToLong(Thread::getId).toArray();

        // 采集 cpu
        Map<Long, Double> threadSampler = threadSampler(threadIds, threadCommandParam.getSamplerInterval());

        List<BaseThreadInfo> threadInfos = new ArrayList<>();

        for (Thread thread : threads) {
            BaseThreadInfo threadInfo = ManagementConverter.CONVERTER.threadToInfo(thread);

            threadInfo.setCpu(threadSampler.get(thread.getId()));

            threadInfos.add(threadInfo);
        }
        threadInfos.sort(Comparator.comparing(BaseThreadInfo::getCpu).reversed());

        ThreadList result = new ThreadList();

        result.setThreadStatistic(mxBeanUtil.getThreadStatistic());
        result.setThreads(threadInfos);

        return result;
    }

    private Map<Long, Double> threadSampler(long[] threadIds, long millis) {
        long[] threadCpuTime = threadMXBean.getThreadCpuTime(threadIds);
        long current = System.nanoTime();
        ThreadUtil.safeSleep(millis);
        long[] newThreadCpuTime = threadMXBean.getThreadCpuTime(threadIds);
        long next = System.nanoTime();
        Map<Long, Double> threadSamplerMap = new HashMap<>();
        long sampleInterval = next - current;
        for (int i = 0; i < threadIds.length; i++) {
            long deltas = newThreadCpuTime[i] - threadCpuTime[i];
            double cpu = Math.round(deltas * 10000.0 / sampleInterval) / 100.0;
            threadSamplerMap.put(threadIds[i], cpu);
        }
        return threadSamplerMap;
    }

    @Override
    public void terminated() {

    }

}
