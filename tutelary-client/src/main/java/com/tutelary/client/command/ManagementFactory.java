package com.tutelary.client.command;

import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.stream.Collectors;

public class ManagementFactory {

    private static final Log LOG = LogFactory.get(ManagementFactory.class);

    public static OperatingSystemMXBean getOperatingSystemMXBean() {
        return (OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
    }

    public static RuntimeMXBean getRuntimeMXBean() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean();
    }

    public static ThreadMXBean getThreadMXBean() {
        return (ThreadMXBean) java.lang.management.ManagementFactory.getThreadMXBean();
    }

    public static ClassLoadingMXBean getClassLoadingMXBean() {
        return java.lang.management.ManagementFactory.getClassLoadingMXBean();
    }

    public static List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
        return java.lang.management.ManagementFactory.getMemoryPoolMXBeans();
    }

    public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
        return java.lang.management.ManagementFactory.getGarbageCollectorMXBeans()
            .stream()
            .map(
                garbageCollectorMXBean -> (GarbageCollectorMXBean) garbageCollectorMXBean)
            .collect(Collectors.toList());
    }

    public static HotSpotDiagnosticMXBean getHotSpotDiagnosticMXBean() {
        return java.lang.management.ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
    }

}