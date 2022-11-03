package com.tutelary.client.command.host;

import com.sun.management.OperatingSystemMXBean;
import com.tutelary.client.command.Command;
import com.tutelary.client.command.ManagementFactory;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.command.HostInfo;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.tutelary.common.constants.TutelaryConstants.KB;

public class HostCommand implements Command<HostInfo> {

    private static final Log LOG = LogFactory.get(HostCommand.class);

    private final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

    @Override
    public HostInfo execute() {
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

    @Override
    public void terminated() {

    }
}
