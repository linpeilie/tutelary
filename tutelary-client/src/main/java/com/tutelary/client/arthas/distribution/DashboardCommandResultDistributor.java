package com.tutelary.client.arthas.distribution;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.taobao.arthas.core.command.model.*;
import com.tutelary.client.arthas.converter.ArthasModelConverter;
import com.tutelary.message.command.*;
import com.tutelary.message.command.domain.DashboardMemoryInfo;
import com.tutelary.message.command.domain.DashboardRuntime;
import com.tutelary.message.command.domain.GarbageCollector;
import com.tutelary.message.command.domain.ThreadInfo;

import java.util.List;
import java.util.Map;

public class DashboardCommandResultDistributor extends AbstractResultDistributor<DashboardCommandResultMessage> {

    public DashboardCommandResultDistributor() {
        super(new DashboardCommandResultMessage());
    }

    @Override
    protected void appendCommandResult(ResultModel resultModel) {
        DashboardModel dashboardModel = (DashboardModel)resultModel;
        analysisRuntime(dashboardModel.getRuntimeInfo());
        analysisGarbageCollector(dashboardModel.getGcInfos());
        analysisThreads(dashboardModel.getThreads());
        analysisMemoryInfo(dashboardModel.getMemoryInfo());
    }

    private void analysisMemoryInfo(Map<String, List<MemoryEntryVO>> memoryInfo) {
        DashboardMemoryInfo memory = new DashboardMemoryInfo();
        memory.setHeap(ArthasModelConverter.CONVERTER.memoryEntriesToMemoryUsageList(memoryInfo.get("heap")));
        memory.setNonHeap(ArthasModelConverter.CONVERTER.memoryEntriesToMemoryUsageList(memoryInfo.get("nonheap")));
        memory.setBufferPool(ArthasModelConverter.CONVERTER.memoryEntriesToMemoryUsageList(memoryInfo.get("buffer_pool")));
        resultMessage.setMemoryInfo(memory);
    }

    private void analysisThreads(List<ThreadVO> threads) {
        List<ThreadInfo> threadInfos =
            ArthasModelConverter.CONVERTER.threadVosToThreadDetailInfoList(threads);
        resultMessage.setThreads(threadInfos);
    }

    private void analysisGarbageCollector(List<GcInfoVO> gcInfos) {
        List<GarbageCollector> garbageCollectors =
            ArthasModelConverter.CONVERTER.gcInfoListToGarbageCollectorList(gcInfos);
        resultMessage.setGarbageCollectors(garbageCollectors);
    }

    private void analysisRuntime(RuntimeInfoVO runtimeInfo) {
        DashboardRuntime dashboardRuntime = ArthasModelConverter.CONVERTER.runtimeInfoVoToDashboardRuntime(runtimeInfo);
        resultMessage.setRuntime(dashboardRuntime);
    }
}
