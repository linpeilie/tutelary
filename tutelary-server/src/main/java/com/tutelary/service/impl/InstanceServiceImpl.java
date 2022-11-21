package com.tutelary.service.impl;

import com.tutelary.bean.api.req.OverviewQueryRequest;
import com.tutelary.bean.domain.*;
import com.tutelary.bean.domain.query.*;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.message.command.result.Overview;
import com.tutelary.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutelary.service.InstanceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstanceServiceImpl implements InstanceService {

    private InstanceRepository instanceRepository;

    private InstanceHostRepository instanceHostRepository;

    private InstanceThreadStatisticRepository instanceThreadStatisticRepository;

    private InstanceJvmMemoryRepository instanceJvmMemoryRepository;

    private InstanceGarbageCollectorsRepository instanceGarbageCollectorsRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addInstance(Instance instance) {
        Instance oldInstance =
                instanceRepository.getByInstanceId(instance.getInstanceId());
        if (oldInstance == null) {
            return instanceRepository.add(instance);
        } else {
            return instanceRepository.validedInstance(instance.getInstanceId());
        }
    }

    @Override
    public boolean invalidInstance(String instanceId) {
        return instanceRepository.invalidedInstance(instanceId);
    }

    @Override
    public Instance getInstanceByInstanceId(String instanceId) {
        return instanceRepository.getByInstanceId(instanceId);
    }

    @Override
    public PageResult<Instance> pageList(InstanceQuery instanceQuery, PageQueryRequest pageRequest) {
        return instanceRepository.pageList(instanceQuery, pageRequest);
    }

    @Override
    public List<Instance> list(InstanceQuery queryParam) {
        return instanceRepository.list(queryParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveReportData(InstanceOverview overview) {
        instanceHostRepository.add(overview.getHost());
        instanceThreadStatisticRepository.add(overview.getThreadStatistic());
        instanceJvmMemoryRepository.addAll(overview.getJvmMemories());
        instanceGarbageCollectorsRepository.addAll(overview.getGarbageCollectors());
    }

    @Override
    public List<InstanceHost> listHostInfo(OverviewQuery query) {
        return instanceHostRepository.list(query);
    }

    @Override
    public List<InstanceThreadStatistic> listThreadStatistics(OverviewQuery query) {
        return instanceThreadStatisticRepository.list(query);
    }

    @Override
    public List<InstanceJvmMemory> listJvmMemories(OverviewQuery query) {
        return instanceJvmMemoryRepository.list(query);
    }

    @Override
    public List<InstanceGarbageCollectors> listGarbageCollectors(OverviewQuery query) {
        return instanceGarbageCollectorsRepository.list(query);
    }

    /************************* setter *******************************/

    @Autowired
    public void setInstanceRepository(InstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    @Autowired
    public void setInstanceHostRepository(InstanceHostRepository instanceHostRepository) {
        this.instanceHostRepository = instanceHostRepository;
    }

    @Autowired
    public void setInstanceThreadStatisticRepository(InstanceThreadStatisticRepository instanceThreadStatisticRepository) {
        this.instanceThreadStatisticRepository = instanceThreadStatisticRepository;
    }

    @Autowired
    public void setInstanceJvmMemoryRepository(InstanceJvmMemoryRepository instanceJvmMemoryRepository) {
        this.instanceJvmMemoryRepository = instanceJvmMemoryRepository;
    }

    @Autowired
    public void setInstanceGarbageCollectorsRepository(InstanceGarbageCollectorsRepository instanceGarbageCollectorsRepository) {
        this.instanceGarbageCollectorsRepository = instanceGarbageCollectorsRepository;
    }
}
