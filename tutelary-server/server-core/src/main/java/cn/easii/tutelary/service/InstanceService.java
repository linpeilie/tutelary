package cn.easii.tutelary.service;

import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.bean.domain.InstanceGarbageCollectors;
import cn.easii.tutelary.bean.domain.InstanceHost;
import cn.easii.tutelary.bean.domain.InstanceJvmMemory;
import cn.easii.tutelary.bean.domain.InstanceOverview;
import cn.easii.tutelary.bean.domain.InstanceThreadStatistic;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;
import java.util.List;

public interface InstanceService {

    boolean addInstance(Instance instanceEntity);

    boolean invalidInstance(String instanceId);

    Instance getInstanceByInstanceId(String instanceId);

    List<Instance> list(InstanceQuery queryParam);

    List<Instance> list(InstanceQuery queryParam, long pageIndex, long pageSize);

    long count(InstanceQuery queryParam);

    List<Instance> listEnabled();

    void saveReportData(InstanceOverview overview);

    List<InstanceHost> listHostInfo(StatisticQuery query);

    InstanceHost getHostInfo(String instanceId);

    List<InstanceThreadStatistic> listThreadStatistics(StatisticQuery query);

    List<InstanceJvmMemory> listJvmMemories(StatisticQuery query);

    List<InstanceGarbageCollectors> listGarbageCollectors(StatisticQuery query);

}
