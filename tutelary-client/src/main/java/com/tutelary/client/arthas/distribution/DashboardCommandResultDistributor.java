package com.tutelary.client.arthas.distribution;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.taobao.arthas.core.command.model.*;
import com.tutelary.client.arthas.converter.ArthasModelConverter;
import com.tutelary.message.command.*;

import java.util.List;
import java.util.Map;

public class DashboardCommandResultDistributor extends AbstractResultDistributor<DashboardCommandResultMessage> {

    private static final Log LOG = LogFactory.get();

    private final DashboardCommandResultMessage resultMessage = new DashboardCommandResultMessage();

    @Override
    public DashboardCommandResultMessage getResult() {
        return resultMessage;
    }

    @Override
    public void appendResult(ResultModel result) {
        LOG.debug("[ PackageResultDistributor ] append result : {}", result);
        if (result instanceof StatusModel) {
            handleStatusModel(result);
        } else if (result instanceof DashboardModel) {
            handleDashboardModel(result);
        }

    }

    private void handleDashboardModel(ResultModel result) {
        DashboardModel dashboardModel = (DashboardModel)result;
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
        List<ThreadDetailInfo> threadDetailInfos =
            ArthasModelConverter.CONVERTER.threadVosToThreadDetailInfoList(threads);
        resultMessage.setThreads(threadDetailInfos);
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

    private void handleStatusModel(ResultModel result) {
        StatusModel statusModel = (StatusModel)result;
        resultMessage.setStatus(statusModel.getStatusCode());
        resultMessage.setMessage(statusModel.getMessage());
    }
}
