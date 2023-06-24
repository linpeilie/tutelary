package com.tutelary.api;

import cn.hutool.core.collection.CollectionUtil;
import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.domain.InstanceThreadStatistic;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.bean.req.InstancePageQueryRequest;
import com.tutelary.bean.req.InstanceQueryRequest;
import com.tutelary.bean.req.StatisticQueryRequest;
import com.tutelary.bean.resp.InstanceDetailInfoResponse;
import com.tutelary.bean.resp.InstanceGarbageCollectorsResponse;
import com.tutelary.bean.resp.InstanceHostResponse;
import com.tutelary.bean.resp.InstanceInfoResponse;
import com.tutelary.bean.resp.InstanceJvmMemoryResponse;
import com.tutelary.bean.resp.InstanceThreadStatisticResponse;
import com.tutelary.bean.resp.JvmStatisticResponse;
import com.tutelary.bean.resp.OverviewResponse;
import com.tutelary.common.bean.R;
import com.tutelary.common.bean.resp.PageResult;
import com.tutelary.common.utils.DateUtils;
import com.tutelary.service.InstanceService;
import io.github.linpeilie.Converter;
import java.lang.management.MemoryType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/instance")
public class InstanceController {

    private static final int KB = 1024;

    private InstanceService instanceService;
    private Converter converter;

    @PostMapping(value = "pageQuery")
    public R<PageResult<InstanceInfoResponse>> pageQuery(@RequestBody InstancePageQueryRequest instancePageQueryParam) {
        InstanceQuery queryParam = converter.convert(instancePageQueryParam, InstanceQuery.class);
        final long count = instanceService.count(queryParam);
        if (count > 0) {
            final List<Instance> list = instanceService.list(queryParam, instancePageQueryParam.getPageIndex(),
                instancePageQueryParam.getPageSize());
            return R.success(PageResult.of(count, converter.convert(list, InstanceInfoResponse.class)));
        }

        return R.success(PageResult.empty());
    }

    @PostMapping(value = "list")
    public R<List<InstanceInfoResponse>> list(@RequestBody InstanceQueryRequest instanceQueryRequest) {
        InstanceQuery queryParam = converter.convert(instanceQueryRequest, InstanceQuery.class);
        List<Instance> list = instanceService.list(queryParam);
        return R.success(converter.convert(list, InstanceInfoResponse.class));
    }

    @GetMapping(value = "detail")
    public R<InstanceDetailInfoResponse> detail(@RequestParam("instanceId") String instanceId) {
        Instance instance = instanceService.getInstanceByInstanceId(instanceId);
        return R.success(converter.convert(instance, InstanceDetailInfoResponse.class));
    }

    @PostMapping(value = "statistic/overview")
    public R<OverviewResponse> overview(@RequestBody StatisticQueryRequest statisticQueryRequest) {
        StatisticQuery query = converter.convert(statisticQueryRequest, StatisticQuery.class);

        InstanceHost instanceHost = instanceService.getHostInfo(query.getInstanceId());
        List<InstanceJvmMemory> instanceJvmMemories = instanceService.listJvmMemories(query);
        List<InstanceThreadStatistic> threadStatistics = instanceService.listThreadStatistics(query);
        List<InstanceGarbageCollectors> instanceGarbageCollectors = instanceService.listGarbageCollectors(query);

        OverviewResponse overview = new OverviewResponse();
        overview.setHost(transHostInfo(instanceHost));
        overview.setThreadStatistic(transThreadStatistic(threadStatistics));
        overview.setHeapMemory(transJvmMemoryInfo(instanceJvmMemories, MemoryType.HEAP));
        overview.setNonHeapMemory(transJvmMemoryInfo(instanceJvmMemories, MemoryType.NON_HEAP));
        overview.setGarbageCollectors(transGarbageCollectors(instanceGarbageCollectors));

        return R.success(overview);
    }

    @PostMapping(value = "statistic/jvm")
    public R<JvmStatisticResponse> jvmStatistic(@RequestBody StatisticQueryRequest statisticQueryRequest) {
        StatisticQuery query = converter.convert(statisticQueryRequest, StatisticQuery.class);

        List<InstanceJvmMemory> instanceJvmMemories = instanceService.listJvmMemories(query);
        List<InstanceGarbageCollectors> instanceGarbageCollectors = instanceService.listGarbageCollectors(query);

        JvmStatisticResponse jvmStatistic = new JvmStatisticResponse();
        jvmStatistic.setHeapMemory(transJvmMemoryInfo(instanceJvmMemories, MemoryType.HEAP));
        jvmStatistic.setNonHeapMemory(transJvmMemoryInfo(instanceJvmMemories, MemoryType.NON_HEAP));
        jvmStatistic.setGarbageCollectors(transGarbageCollectors(instanceGarbageCollectors));

        return R.success(jvmStatistic);
    }

    private List<InstanceGarbageCollectorsResponse> transGarbageCollectors(List<InstanceGarbageCollectors> garbageCollectors) {
        return garbageCollectors.stream()
            .collect(Collectors.groupingBy(InstanceGarbageCollectors::getName))
            .values().stream()
            .filter(CollectionUtil::isNotEmpty)
            .map(list -> {
                InstanceGarbageCollectorsResponse response = new InstanceGarbageCollectorsResponse();
                response.setName(CollectionUtil.getFirst(list).getName());
                response.setMemoryPoolNames(CollectionUtil.getFirst(list).getMemoryPoolNames());
                response.setCollectionCounts(
                    list.stream().map(InstanceGarbageCollectors::getCollectionCount)
                        .collect(Collectors.toList()));
                response.setCollectionTimes(
                    list.stream().map(InstanceGarbageCollectors::getCollectionTime)
                        .collect(Collectors.toList()));
                response.setReportTimestamps(
                    list.stream().map(item -> DateUtils.getTimestamp(item.getReportTime()))
                        .collect(Collectors.toList()));
                return response;
            }).collect(Collectors.toList());
    }

    private List<InstanceJvmMemoryResponse> transJvmMemoryInfo(List<InstanceJvmMemory> jvmMemories,
                                                               MemoryType memoryType) {
        if (CollectionUtil.isEmpty(jvmMemories)) {
            return null;
        }
        return jvmMemories.stream().filter(memoryInfo -> memoryType.name().equals(memoryInfo.getType()))
            .collect(Collectors.groupingBy(InstanceJvmMemory::getName))
            .values().stream()
            .filter(CollectionUtil::isNotEmpty)
            .map(list -> {
                InstanceJvmMemoryResponse instanceJvmMemoryResponse = new InstanceJvmMemoryResponse();
                instanceJvmMemoryResponse.setType(CollectionUtil.getFirst(list).getType());
                instanceJvmMemoryResponse.setName(CollectionUtil.getFirst(list).getName());
                instanceJvmMemoryResponse.setUsed(list.stream().map(InstanceJvmMemory::getUsed)
                    .map(val -> val / KB)
                    .collect(Collectors.toList()));
                instanceJvmMemoryResponse.setCommitted(list.stream().map(InstanceJvmMemory::getCommitted)
                    .map(val -> val / KB)
                    .collect(Collectors.toList()));
                instanceJvmMemoryResponse.setMax(list.stream().map(InstanceJvmMemory::getMax)
                    .map(val -> val / KB)
                    .collect(Collectors.toList()));
                instanceJvmMemoryResponse.setReportTimestamps(
                    list.stream().map(item -> DateUtils.getTimestamp(item.getReportTime()))
                        .collect(Collectors.toList()));
                return instanceJvmMemoryResponse;
            }).collect(Collectors.toList());
    }

    private InstanceThreadStatisticResponse transThreadStatistic(List<InstanceThreadStatistic> threadStatistics) {
        if (CollectionUtil.isEmpty(threadStatistics)) {
            return null;
        }
        InstanceThreadStatisticResponse instanceThreadStatisticResponse = new InstanceThreadStatisticResponse();
        instanceThreadStatisticResponse.setThreadCount(
            threadStatistics.stream().map(InstanceThreadStatistic::getThreadCount).collect(Collectors.toList()));
        instanceThreadStatisticResponse.setPeakThreadCount(
            threadStatistics.stream().map(InstanceThreadStatistic::getThreadCount).collect(Collectors.toList()));
        instanceThreadStatisticResponse.setDaemonThreadCount(
            threadStatistics.stream().map(InstanceThreadStatistic::getThreadCount).collect(Collectors.toList()));
        instanceThreadStatisticResponse.setTotalStartedThreadCount(
            CollectionUtil.getLast(threadStatistics).getTotalStartedThreadCount());
        instanceThreadStatisticResponse.setReportTimestamps(threadStatistics.stream()
            .map(statistic -> DateUtils.getTimestamp(
                statistic.getReportTime()))
            .collect(Collectors.toList()));
        return instanceThreadStatisticResponse;
    }

    private InstanceHostResponse transHostInfo(InstanceHost host) {
        if (host == null) {
            return null;
        }
        InstanceHostResponse instanceHostResponse = new InstanceHostResponse();
        instanceHostResponse.setHostName(host.getHostName());
        instanceHostResponse.setHostAddress(host.getHostAddress());
        instanceHostResponse.setOsName(host.getOsName());
        instanceHostResponse.setArch(host.getArch());
        instanceHostResponse.setAvailableProcessors(host.getAvailableProcessors());
        instanceHostResponse.setCommittedVirtualMemory(host.getCommittedVirtualMemory());
        instanceHostResponse.setTotalPhysicalMemorySize(host.getTotalPhysicalMemorySize());
        instanceHostResponse.setFreePhysicalMemorySize(host.getFreePhysicalMemorySize());
        instanceHostResponse.setTotalSwapSpaceSize(host.getTotalSwapSpaceSize());
        instanceHostResponse.setFreeSwapSpaceSize(host.getFreeSwapSpaceSize());
        instanceHostResponse.setDiskFreeSpace(host.getDiskFreeSpace());
        instanceHostResponse.setDiskUsableSpace(host.getDiskUsableSpace());
        instanceHostResponse.setDiskTotalSpace(host.getDiskTotalSpace());
        instanceHostResponse.setReportTimestamps(DateUtils.getTimestamp(host.getReportTime()));
        return instanceHostResponse;
    }

    /************************* setter *************************/
    @Autowired
    public InstanceController setInstanceService(InstanceService instanceService) {
        this.instanceService = instanceService;
        return this;
    }

    @Autowired
    public void setConverter(final Converter converter) {
        this.converter = converter;
    }
}
