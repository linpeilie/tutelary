package cn.easii.tutelary.api;

import cn.easii.tutelary.bean.req.InstancePageQueryRequest;
import cn.easii.tutelary.bean.req.InstanceQueryRequest;
import cn.easii.tutelary.bean.req.StatisticQueryRequest;
import cn.easii.tutelary.bean.resp.InstanceDetailInfoResponse;
import cn.easii.tutelary.bean.resp.InstanceGarbageCollectorsResponse;
import cn.easii.tutelary.bean.resp.InstanceHostResponse;
import cn.easii.tutelary.bean.resp.InstanceInfoResponse;
import cn.easii.tutelary.bean.resp.InstanceJvmMemoryResponse;
import cn.easii.tutelary.bean.resp.InstanceThreadStatisticResponse;
import cn.easii.tutelary.bean.resp.JvmStatisticResponse;
import cn.easii.tutelary.bean.resp.OverviewResponse;
import cn.easii.tutelary.common.bean.R;
import cn.easii.tutelary.common.bean.resp.PageResult;
import cn.easii.tutelary.service.AppService;
import cn.easii.tutelary.service.InstanceService;
import cn.hutool.core.collection.CollectionUtil;
import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.bean.domain.InstanceGarbageCollectors;
import cn.easii.tutelary.bean.domain.InstanceHost;
import cn.easii.tutelary.bean.domain.InstanceJvmMemory;
import cn.easii.tutelary.bean.domain.InstanceThreadStatistic;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;
import cn.easii.tutelary.common.utils.DateUtils;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.management.MemoryType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/instance")
@RequiredArgsConstructor
public class InstanceController {

    private static final int KB = 1024;

    private final InstanceService instanceService;
    private final AppService appService;
    private final Converter converter;

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

    @PostMapping(value = "listByAppName")
    @Operation(summary = "获取实例列表", description = "根据应用名称获取实例列表")
    @ApiResponse(description = "实例信息", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = InstanceInfoResponse.class)))
    public R<List<InstanceInfoResponse>> listByAppName(@RequestParam("appName") String appName) {
        if (StrUtil.isEmpty(appName)) {
            return R.success(Lists.newArrayList());
        }
        List<String> instanceIds = appService.listInstanceIdsByAppName(appName);
        if (CollectionUtil.isEmpty(instanceIds)) {
            return R.success(Lists.newArrayList());
        }
        InstanceQuery instanceQuery = new InstanceQuery();
        instanceQuery.setInstanceIds(instanceIds);
        List<Instance> list = instanceService.list(instanceQuery);
        return R.success(converter.convert(list, InstanceInfoResponse.class));
    }

    @PostMapping(value = "detail")
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
}
