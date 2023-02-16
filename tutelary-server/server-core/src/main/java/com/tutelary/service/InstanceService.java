package com.tutelary.service;

import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.domain.InstanceOverview;
import com.tutelary.bean.domain.InstanceThreadStatistic;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;
import java.util.List;

public interface InstanceService {

    boolean addInstance(Instance instanceEntity);

    boolean invalidInstance(String instanceId);

    Instance getInstanceByInstanceId(String instanceId);

    PageResult<Instance> pageList(InstanceQuery instanceQuery, PageQueryRequest pageRequest);

    List<Instance> list(InstanceQuery queryParam);

    List<Instance> listEnabled();

    void saveReportData(InstanceOverview overview);

    List<InstanceHost> listHostInfo(StatisticQuery query);

    InstanceHost getHostInfo(String instanceId);

    List<InstanceThreadStatistic> listThreadStatistics(StatisticQuery query);

    List<InstanceJvmMemory> listJvmMemories(StatisticQuery query);

    List<InstanceGarbageCollectors> listGarbageCollectors(StatisticQuery query);

}
