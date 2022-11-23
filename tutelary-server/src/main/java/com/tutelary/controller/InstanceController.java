package com.tutelary.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.tutelary.bean.api.req.InstancePageQueryRequest;
import com.tutelary.bean.api.req.InstanceQueryRequest;
import com.tutelary.bean.api.req.OverviewQueryRequest;
import com.tutelary.bean.api.resp.*;
import com.tutelary.bean.converter.*;
import com.tutelary.bean.domain.*;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.domain.query.OverviewQuery;
import com.tutelary.common.bean.api.R;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.common.utils.DateUtils;
import com.tutelary.message.command.result.Overview;
import com.tutelary.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.management.MemoryType;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping (value = "/api/instance")
public class InstanceController {

    private InstanceService instanceService;
    private InstanceConverter instanceConverter;
    private InstanceHostConverter instanceHostConverter;
    private InstanceThreadStatisticConverter instanceThreadStatisticConverter;
    private InstanceJvmMemoryConverter instanceJvmMemoryConverter;
    private InstanceGarbageCollectorConverter instanceGarbageCollectorConverter;


    @PostMapping (value = "pageQuery")
    public R<PageResult<InstanceInfoResponse>> pageQuery(@RequestBody InstancePageQueryRequest instancePageQueryParam) {
        InstanceQuery queryParam = instanceConverter.pageQueryReqToDomain(instancePageQueryParam);
        PageResult<Instance> pageResult = instanceService.pageList(queryParam, instancePageQueryParam);
        return R.success(instanceConverter.domainPageResultToResponse(pageResult));
    }

    @PostMapping(value = "list")
    public R<List<InstanceInfoResponse>> list(@RequestBody InstanceQueryRequest instanceQueryRequest) {
        InstanceQuery queryParam = instanceConverter.queryRequestToDomain(instanceQueryRequest);
        List<Instance> list = instanceService.list(queryParam);
        return R.success(instanceConverter.domainListToResponse(list));
    }

    @GetMapping(value = "detail")
    public R<InstanceDetailInfoResponse> detail(@RequestParam("instanceId") String instanceId) {
        Instance instance = instanceService.getInstanceByInstanceId(instanceId);
        return R.success(instanceConverter.instanceToDetailResponse(instance));
    }

    @PostMapping(value = "overview")
    public R<OverviewResponse> overview(@RequestBody OverviewQueryRequest overviewQueryRequest) {
        OverviewQuery query = instanceConverter.overviewQueryRequestToDomain(overviewQueryRequest);

        List<InstanceHost> instanceHosts = instanceService.listHostInfo(query);
        List<InstanceJvmMemory> instanceJvmMemories = instanceService.listJvmMemories(query);
        List<InstanceThreadStatistic> threadStatistics = instanceService.listThreadStatistics(query);
        List<InstanceGarbageCollectors> instanceGarbageCollectors = instanceService.listGarbageCollectors(query);

        OverviewResponse overview = new OverviewResponse();
        overview.setHost(transHostInfo(instanceHosts));
        overview.setThreadStatistic(transThreadStatistic(threadStatistics));
        overview.setHeapMemory(transJvmMemoryInfo(instanceJvmMemories, MemoryType.HEAP));
        overview.setNonHeapMemory(transJvmMemoryInfo(instanceJvmMemories, MemoryType.NON_HEAP));
        overview.setGarbageCollectors(transGarbageCollectors(instanceGarbageCollectors));

        return R.success(overview);
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
                    response.setCollectionCounts(list.stream().map(InstanceGarbageCollectors::getCollectionCount)
                            .collect(Collectors.toList()));
                    response.setCollectionTimes(list.stream().map(InstanceGarbageCollectors::getCollectionTime)
                            .collect(Collectors.toList()));
                    response.setReportTimestamps(list.stream().map(item -> DateUtils.getTimestamp(item.getReportTime()))
                            .collect(Collectors.toList()));
                    return response;
                }).collect(Collectors.toList());
    }


    private List<InstanceJvmMemoryResponse> transJvmMemoryInfo(List<InstanceJvmMemory> jvmMemories, MemoryType memoryType) {
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
                            .collect(Collectors.toList()));
                    instanceJvmMemoryResponse.setCommitted(list.stream().map(InstanceJvmMemory::getCommitted)
                            .collect(Collectors.toList()));
                    instanceJvmMemoryResponse.setMax(list.stream().map(InstanceJvmMemory::getMax)
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
                .map(statistic -> DateUtils.getTimestamp(statistic.getReportTime()))
                .collect(Collectors.toList()));
        return instanceThreadStatisticResponse;
    }

    private InstanceHostResponse transHostInfo(List<InstanceHost> hosts) {
        if (CollectionUtil.isEmpty(hosts)) {
            return null;
        }
        InstanceHostResponse instanceHostResponse = new InstanceHostResponse();
        instanceHostResponse.setHostName(hosts.get(0).getHostName());
        instanceHostResponse.setHostAddress(hosts.get(0).getHostAddress());
        instanceHostResponse.setOsName(hosts.get(0).getOsName());
        instanceHostResponse.setArch(hosts.get(0).getArch());
        instanceHostResponse.setAvailableProcessors(hosts.stream().map(InstanceHost::getAvailableProcessors)
                .collect(Collectors.toList()));
        instanceHostResponse.setCommittedVirtualMemory(hosts.stream().map(InstanceHost::getCommittedVirtualMemory)
                .collect(Collectors.toList()));
        instanceHostResponse.setTotalPhysicalMemorySize(hosts.stream().map(InstanceHost::getTotalPhysicalMemorySize)
                .collect(Collectors.toList()));
        instanceHostResponse.setFreePhysicalMemorySize(hosts.stream().map(InstanceHost::getFreePhysicalMemorySize)
                .collect(Collectors.toList()));
        instanceHostResponse.setTotalSwapSpaceSize(hosts.stream().map(InstanceHost::getTotalSwapSpaceSize)
                .collect(Collectors.toList()));
        instanceHostResponse.setFreeSwapSpaceSize(hosts.stream().map(InstanceHost::getFreeSwapSpaceSize)
                .collect(Collectors.toList()));
        instanceHostResponse.setDiskFreeSpace(CollectionUtil.getLast(hosts).getDiskFreeSpace());
        instanceHostResponse.setDiskUsableSpace(CollectionUtil.getLast(hosts).getDiskUsableSpace());
        instanceHostResponse.setDiskTotalSpace(CollectionUtil.getLast(hosts).getDiskTotalSpace());
        instanceHostResponse.setReportTimestamps(hosts.stream()
                .map(host -> DateUtils.getTimestamp(host.getReportTime()))
                .collect(Collectors.toList()));
        return instanceHostResponse;
    }


    /************************* setter *************************/
    @Autowired
    public InstanceController setInstanceService(InstanceService instanceService) {
        this.instanceService = instanceService;
        return this;
    }

    @Autowired
    public InstanceController setInstanceConverter(InstanceConverter instanceConverter) {
        this.instanceConverter = instanceConverter;
        return this;
    }

    @Autowired
    public InstanceController setInstanceHostConverter(InstanceHostConverter instanceHostConverter) {
        this.instanceHostConverter = instanceHostConverter;
        return this;
    }

    @Autowired
    public InstanceController setInstanceThreadStatisticConverter(InstanceThreadStatisticConverter instanceThreadStatisticConverter) {
        this.instanceThreadStatisticConverter = instanceThreadStatisticConverter;
        return this;
    }

    @Autowired
    public InstanceController setInstanceJvmMemoryConverter(InstanceJvmMemoryConverter instanceJvmMemoryConverter) {
        this.instanceJvmMemoryConverter = instanceJvmMemoryConverter;
        return this;
    }

    @Autowired
    public InstanceController setInstanceGarbageCollectorConverter(InstanceGarbageCollectorConverter instanceGarbageCollectorConverter) {
        this.instanceGarbageCollectorConverter = instanceGarbageCollectorConverter;
        return this;
    }
}
