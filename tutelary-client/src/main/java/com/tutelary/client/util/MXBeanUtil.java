package com.tutelary.client.util;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import com.tutelary.client.command.ManagementFactory;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.command.domain.*;

import java.io.File;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.tutelary.common.constants.TutelaryConstants.KB;

public class MXBeanUtil {

    private static final Log LOG = LogFactory.get(MXBeanUtil.class);

    private final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    private final List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();

    private final List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();

    public static MXBeanUtil getInstance() {
        return new MXBeanUtil();
    }

    public HostInfo getHostInfo() {
        HostInfo hostInfo = new HostInfo();
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostInfo.setHostName(localHost.getHostName());
            hostInfo.setHostAddress(localHost.getHostAddress());
        } catch (UnknownHostException e) {
            LOG.error("[HostCommand] InetAddress.getLocalHost error", e);
        }
        hostInfo.setOsName(operatingSystemMXBean.getName());
        hostInfo.setArch(operatingSystemMXBean.getArch());
        hostInfo.setAvailableProcessors(operatingSystemMXBean.getAvailableProcessors());
        hostInfo.setCommittedVirtualMemory(operatingSystemMXBean.getCommittedVirtualMemorySize() / KB);
        hostInfo.setTotalPhysicalMemorySize(operatingSystemMXBean.getTotalPhysicalMemorySize() / KB);
        hostInfo.setFreePhysicalMemorySize(operatingSystemMXBean.getFreePhysicalMemorySize() / KB);
        hostInfo.setTotalSwapSpaceSize(operatingSystemMXBean.getTotalSwapSpaceSize() / KB);
        hostInfo.setFreeSwapSpaceSize(operatingSystemMXBean.getFreeSwapSpaceSize() / KB);
        File[] files = File.listRoots();
        long diskFreeSpace = 0;
        long diskUsableSpace = 0;
        long diskTotalSpace = 0;
        for (File file : files) {
            diskFreeSpace += file.getFreeSpace();
            diskUsableSpace += file.getUsableSpace();
            diskTotalSpace += file.getTotalSpace();
        }
        hostInfo.setDiskFreeSpace(diskFreeSpace / KB);
        hostInfo.setDiskUsableSpace(diskUsableSpace / KB);
        hostInfo.setDiskTotalSpace(diskTotalSpace);
        return hostInfo;
    }

    public JvmInfo getJvmInfo() {
        JvmInfo jvmInfo = new JvmInfo();
        jvmInfo.setInputArguments(runtimeMXBean.getInputArguments());
        jvmInfo.setSystemProperties(runtimeMXBean.getSystemProperties());
        jvmInfo.setClassPath(runtimeMXBean.getClassPath());
        jvmInfo.setLibraryPath(runtimeMXBean.getLibraryPath());
        jvmInfo.setVmVendor(runtimeMXBean.getVmVendor());
        jvmInfo.setVmName(runtimeMXBean.getVmName());
        jvmInfo.setVmVersion(runtimeMXBean.getVmVersion());
        jvmInfo.setJdkVersion(runtimeMXBean.getSystemProperties().get("java.runtime.version"));
        jvmInfo.setStartTime(runtimeMXBean.getStartTime());
        return jvmInfo;
    }

    public ThreadStatistic getThreadStatistic() {
        ThreadStatistic threadStatistic = new ThreadStatistic();
        threadStatistic.setThreadCount(threadMXBean.getThreadCount());
        threadStatistic.setPeakThreadCount(threadMXBean.getPeakThreadCount());
        threadStatistic.setDaemonThreadCount(threadMXBean.getDaemonThreadCount());
        threadStatistic.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
        return threadStatistic;
    }

    public List<JvmMemory> getJvmMemoryInfo(MemoryType memoryType) {
        return memoryPoolMXBeans.stream().filter(memoryPoolMXBean -> memoryType.equals(memoryPoolMXBean.getType())).map(memoryPoolMXBean -> {
            JvmMemory jvmMemory = new JvmMemory();
            jvmMemory.setName(memoryPoolMXBean.getName());
            MemoryUsage memoryUsage = memoryPoolMXBean.getUsage();
            jvmMemory.setUsed(memoryUsage.getUsed() / KB);
            jvmMemory.setCommitted(memoryUsage.getCommitted() / KB);
            jvmMemory.setMax(memoryUsage.getMax() / KB);
            return jvmMemory;
        }).collect(Collectors.toList());
    }

    public List<GarbageCollector> getGarbageCollectors() {
        return garbageCollectorMXBeans.stream().map(garbageCollectorMXBean -> {
            GarbageCollector garbageCollector = new GarbageCollector();
            garbageCollector.setName(garbageCollectorMXBean.getName());
            garbageCollector.setCollectionCount(garbageCollectorMXBean.getCollectionCount());
            garbageCollector.setCollectionTime(garbageCollectorMXBean.getCollectionTime());
            garbageCollector.setMemoryPoolNames(Arrays.asList(garbageCollectorMXBean.getMemoryPoolNames()));
            return garbageCollector;
        }).collect(Collectors.toList());
    }

}
