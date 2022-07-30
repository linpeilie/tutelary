package com.tutelary.controller;

import com.tutelary.bean.api.req.InstancePageQueryRequest;
import com.tutelary.bean.api.req.InstanceQueryRequest;
import com.tutelary.bean.api.resp.InstanceInfoResponse;
import com.tutelary.bean.converter.InstanceConverter;
import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.common.bean.api.R;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (value = "/api/instance")
public class InstanceController {

    @Autowired
    private InstanceService instanceService;
    @Autowired
    private InstanceConverter instanceConverter;

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
    public R<InstanceInfoResponse> detail(@RequestParam("instanceId") String instanceId) {
        Instance instance = instanceService.getInstanceByInstanceId(instanceId);
        return R.success(instanceConverter.domainToResponse(instance));
    }

}
