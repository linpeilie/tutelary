package com.tutelary.service;

import com.tutelary.bean.api.req.OverviewQueryRequest;
import com.tutelary.bean.domain.*;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.domain.query.OverviewQuery;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.message.command.result.Overview;

import java.util.List;

public interface InstanceService {

    boolean addInstance(Instance instanceEntity);

    boolean invalidInstance(String instanceId);

    Instance getInstanceByInstanceId(String instanceId);

    PageResult<Instance> pageList(InstanceQuery instanceQuery, PageQueryRequest pageRequest);

    List<Instance> list(InstanceQuery queryParam);

    void saveReportData(InstanceOverview overview);

    List<InstanceHost> listHostInfo(OverviewQuery query);

    List<InstanceThreadStatistic> listThreadStatistics(OverviewQuery query);

    List<InstanceJvmMemory> listJvmMemories(OverviewQuery query);

    List<InstanceGarbageCollectors> listGarbageCollectors(OverviewQuery query);

}
