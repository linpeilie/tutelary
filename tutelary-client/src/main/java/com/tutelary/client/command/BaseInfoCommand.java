package com.tutelary.client.command;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import com.tutelary.client.core.perf.PerfData;
import com.tutelary.client.util.RuntimeUtil;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.command.InstanceBaseInfo;
import com.tutelary.message.command.domain.HostBaseInfo;
import sun.management.counter.Counter;

import java.io.File;
import java.lang.management.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class BaseInfoCommand implements Command<InstanceBaseInfo> {

    private static final Log LOG = LogFactory.get(BaseInfoCommand.class);

    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    private final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

    private static ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();

    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    private final MemoryMXBean memoryMXBean = java.lang.management.ManagementFactory.getMemoryMXBean();

    private final List<MemoryPoolMXBean> memoryPoolMXBeans = java.lang.management.ManagementFactory.getMemoryPoolMXBeans();

    @Override
    public InstanceBaseInfo execute() {
        InstanceBaseInfo instanceBaseInfo = new InstanceBaseInfo();
        instanceBaseInfo.setHostInfo(getHostBaseInfo());
        // 主机概况
        testHostInfo();
        // JVM 概况
        testJvmInfo();
        // 类加载概况
        testClassLoadInfo();
        // 线程概况
        testThreadInfo();
        // 内存使用情况
        testMemoryMxBean();
        testMemoryPoolMxBean();
//        testPerfData();
        return instanceBaseInfo;
    }

    private void testClassLoadInfo() {
        LOG.info("********************************************** ThreadInfo **********************************************");
        LOG.info("loadedClassCount : [ {} ]", classLoadingMXBean.getLoadedClassCount());
        LOG.info("totalLoadedClassCount : [ {} ]", classLoadingMXBean.getTotalLoadedClassCount());
        LOG.info("unloadedClassCount : [ {} ]", classLoadingMXBean.getUnloadedClassCount());
        LOG.info("********************************************** ThreadInfo **********************************************\n\n");
    }

    private void testThreadInfo() {
        LOG.info("********************************************** ThreadInfo **********************************************");
        LOG.info("threadCount : [ {} ]", threadMXBean.getThreadCount());
        LOG.info("peakThreadCount : [ {} ]", threadMXBean.getPeakThreadCount());
        LOG.info("daemonThreadCount : [ {} ]", threadMXBean.getDaemonThreadCount());
        LOG.info("totalStartedThreadCount : [ {} ]", threadMXBean.getTotalStartedThreadCount());
        LOG.info("********************************************** ThreadInfo **********************************************\n\n");
    }

    private void testJvmInfo() {
        LOG.info("********************************************** JvmInfo **********************************************");
        LOG.info("inputArguments : [ {} ]", runtimeMXBean.getInputArguments());
        LOG.info("classPath : [ {} ]", runtimeMXBean.getClassPath());
        LOG.info("libraryPath : [ {} ]", runtimeMXBean.getLibraryPath());
        LOG.info("vmVendor : [ {} ]", runtimeMXBean.getVmVendor());
        LOG.info("vmName : [ {} ]", runtimeMXBean.getVmName());
        LOG.info("vmVersion : [ {} ]", runtimeMXBean.getVmVersion());
        LOG.info("jdkVersion : [ {} ]", runtimeMXBean.getSystemProperties().get("java.runtime.version"));
        LOG.info("startTime : [ {} ]", runtimeMXBean.getStartTime());
        LOG.info("processCpuTime : [ {} ]", operatingSystemMXBean.getProcessCpuTime());
        LOG.info("********************************************** JvmInfo **********************************************\n\n");
    }

    private void testHostInfo() {
        LOG.info("********************************************** HostInfo **********************************************");
        LOG.info("osName : [ {} ]", operatingSystemMXBean.getName());
        LOG.info("arch : [ {} ]", operatingSystemMXBean.getArch());
        LOG.info("availableProcessors : [ {} ]", operatingSystemMXBean.getAvailableProcessors());
        LOG.info("committedVirtualMemory : [ {} ]", operatingSystemMXBean.getCommittedVirtualMemorySize());
        LOG.info("totalPhysicalMemorySize : [ {} ]", operatingSystemMXBean.getTotalPhysicalMemorySize());
        LOG.info("freePhysicalMemorySize : [ {} ]", operatingSystemMXBean.getFreePhysicalMemorySize());
        LOG.info("totalSwapSpaceSize : [ {} ]", operatingSystemMXBean.getTotalSwapSpaceSize());
        LOG.info("freeSwapSpaceSize : [ {} ]", operatingSystemMXBean.getFreeSwapSpaceSize());
        File[] files = File.listRoots();
        long diskFreeSpace = 0;
        long diskUsableSpace = 0;
        long diskTotalSpace = 0;
        for (File file : files) {
            LOG.debug("path : {}, free space : {}, usable space");
            diskFreeSpace += file.getFreeSpace();
            diskUsableSpace += file.getUsableSpace();
            diskTotalSpace += file.getTotalSpace();
        }
        LOG.info("diskFreeSpace : [ {} ]", diskFreeSpace);
        LOG.info("diskUsableSpace : [ {} ]", diskUsableSpace);
        LOG.info("diskTotalSpace : [ {} ]", diskTotalSpace);
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostName = localHost.getHostName();
            String hostAddress = localHost.getHostAddress();
            LOG.info("hostName : [ {} ]", hostName);
            LOG.info("hostAddress : [ {} ]", hostAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        LOG.info("********************************************** RuntimeMxBean **********************************************\n\n");
    }

    private void testPerfData() {
        PerfData perfData = PerfData.connect(RuntimeUtil.getPid());
        for (Counter counter : perfData.getAllCounters().values()) {
            LOG.info("counter name : {}, value : {}, unit : {}", counter.getName(), counter.getValue(), counter.getUnits().toString());
        }
    }

    private HostBaseInfo getHostBaseInfo() {
        HostBaseInfo hostBaseInfo = new HostBaseInfo();
        hostBaseInfo.setOsName(operatingSystemMXBean.getName());
        hostBaseInfo.setOsVersion(operatingSystemMXBean.getVersion());
        hostBaseInfo.setAvailableProcessors(operatingSystemMXBean.getAvailableProcessors());
        return hostBaseInfo;
    }

    private void testMemoryMxBean() {
        LOG.info("********************************************** MemoryMxBean **********************************************");
        LOG.info("heapMemoryUsage [ {} ] - 返回用于对象分配的堆的当前内存使用量", memoryMXBean.getHeapMemoryUsage());
        LOG.info("nonHeapMemoryUsage [ {} ] - 非堆内存的当前内存使用量", memoryMXBean.getNonHeapMemoryUsage());
        LOG.info("********************************************** MemoryMxBean **********************************************\n\n");
    }

    private void testMemoryPoolMxBean() {
        LOG.info("********************************************** MemoryPoolMxBean **********************************************");
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            LOG.info("name [ {} ] —— 此内存池的名称", memoryPoolMXBean.getName());
            LOG.info("type [ {} ] —— 内存池的类型", memoryPoolMXBean.getType().toString());
            LOG.info("memoryUsage : [ {} ] Java 虚拟机最近回收了此内存池中的不使用的对象之后的内存使用量", memoryPoolMXBean.getCollectionUsage());
            if (memoryPoolMXBean.isCollectionUsageThresholdSupported()) {
                LOG.info("collectionUsageThreshold [ {} ], 此内存池的回收使用量阈值（以字节为单位）", memoryPoolMXBean.getCollectionUsageThreshold());
                LOG.info("collectionUsageThresholdCount [ {} ]，返回 Java 虚拟机已检测到内存使用量达到或超过回收使用量阈值的次数", memoryPoolMXBean.getCollectionUsageThresholdCount());
            }
            LOG.info("peakUsage [ {} ] Java 虚拟机启动以来或自峰值重置以来此内存池的峰值内存使用量", memoryPoolMXBean.getPeakUsage());
            LOG.info("usage [ {} ], 内存池的内存使用量的估计数", memoryPoolMXBean.getUsage());
            if (memoryPoolMXBean.isUsageThresholdSupported()) {
                LOG.info("usageThreshold [ {} ]，内存池使用量阈值", memoryPoolMXBean.getUsageThreshold());
                LOG.info("usageThresholdCount [ {} ]，内存使用量超过其阈值的次数", memoryPoolMXBean.getUsageThresholdCount());
            }
            LOG.info("#################################################");
        }
        LOG.info("********************************************** MemoryPoolMxBean **********************************************\n\n");
    }

    @Override
    public void terminated() {

    }
}
