package com.tutelary.controller;

import com.tutelary.bean.api.req.InstancePageQueryRequest;
import com.tutelary.bean.api.req.InstanceQueryRequest;
import com.tutelary.bean.api.req.OverviewQueryRequest;
import com.tutelary.bean.api.resp.InstanceDetailInfoResponse;
import com.tutelary.bean.api.resp.InstanceInfoResponse;
import com.tutelary.bean.api.resp.OverviewResponse;
import com.tutelary.bean.converter.*;
import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.domain.query.OverviewQuery;
import com.tutelary.common.bean.api.R;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.message.command.result.Overview;
import com.tutelary.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.management.MemoryType;
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
        OverviewResponse overview = new OverviewResponse();
        overview.setHosts(instanceHostConverter.domainListToResponse(instanceService.listHostInfo(query)));
        overview.setThreadStatistics(instanceThreadStatisticConverter
                .domainListToResponse(instanceService.listThreadStatistics(query)));
        List<InstanceJvmMemory> instanceJvmMemories = instanceService.listJvmMemories(query);
        overview.setHeapMemory(instanceJvmMemoryConverter.domainListToResponse(
                instanceJvmMemories.stream().filter(memory -> MemoryType.HEAP.name().equals(memory.getType()))
                        .collect(Collectors.toList()))
        );
        overview.setNonHeapMemory(instanceJvmMemoryConverter.domainListToResponse(
                instanceJvmMemories.stream().filter(memory -> MemoryType.NON_HEAP.name().equals(memory.getType()))
                        .collect(Collectors.toList()))
        );
        overview.setGarbageCollectors(instanceGarbageCollectorConverter
                .domainListToResponse(instanceService.listGarbageCollectors(query)));
        return R.success(overview);
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
