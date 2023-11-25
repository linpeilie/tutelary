package cn.easii.tutelary.client.command.overview;

import cn.easii.tutelary.message.command.result.Overview;
import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.client.util.MXBeanUtil;
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
