package com.tutelary.client.command.overview;

import com.tutelary.client.command.Command;
import com.tutelary.client.util.MXBeanUtil;
import com.tutelary.message.command.result.Overview;
import java.lang.management.MemoryType;

public class OverviewCommand implements Command<Overview> {

    private final MXBeanUtil mxBeanUtil = MXBeanUtil.getInstance();

    @Override
    public Overview execute() {
        Overview overview = new Overview();
        overview.setHostInfo(mxBeanUtil.getHostInfo());
        overview.setThreadStatistic(mxBeanUtil.getThreadStatistic());
        overview.setHeapMemory(mxBeanUtil.getJvmMemoryInfo(MemoryType.HEAP));
        overview.setNonHeapMemory(mxBeanUtil.getJvmMemoryInfo(MemoryType.NON_HEAP));
        overview.setGarbageCollectors(mxBeanUtil.getGarbageCollectors());
        return overview;
    }

    @Override
    public void terminated() {

    }

}
