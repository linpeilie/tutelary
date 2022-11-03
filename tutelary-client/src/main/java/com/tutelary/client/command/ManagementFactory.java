package com.tutelary.client.command;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.RuntimeMXBean;

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

}