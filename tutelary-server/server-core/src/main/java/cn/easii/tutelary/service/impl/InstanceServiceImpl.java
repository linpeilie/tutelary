package cn.easii.tutelary.service.impl;

import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.bean.domain.InstanceGarbageCollectors;
import cn.easii.tutelary.bean.domain.InstanceHost;
import cn.easii.tutelary.bean.domain.InstanceJvmMemory;
import cn.easii.tutelary.bean.domain.InstanceOverview;
import cn.easii.tutelary.bean.domain.InstanceThreadStatistic;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;
import cn.easii.tutelary.dao.InstanceDAO;
import cn.easii.tutelary.dao.InstanceGarbageCollectorsDAO;
import cn.easii.tutelary.dao.InstanceHostDAO;
import cn.easii.tutelary.dao.InstanceJvmMemoryDAO;
import cn.easii.tutelary.dao.InstanceThreadStatisticDAO;
import cn.easii.tutelary.service.InstanceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InstanceServiceImpl implements InstanceService {

    private InstanceDAO instanceDAO;

    private InstanceHostDAO instanceHostDAO;

    private InstanceThreadStatisticDAO instanceThreadStatisticDAO;

    private InstanceJvmMemoryDAO instanceJvmMemoryDAO;

    private InstanceGarbageCollectorsDAO instanceGarbageCollectorsDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addInstance(Instance instance) {
        Instance oldInstance = instanceDAO.getByInstanceId(instance.getInstanceId());
        if (oldInstance == null) {
            return instanceDAO.add(instance);
        } else {
            return instanceDAO.validedInstance(instance.getInstanceId());
        }
    }

    @Override
    public boolean invalidInstance(String instanceId) {
        return instanceDAO.invalidedInstance(instanceId);
    }

    @Override
    public Instance getInstanceByInstanceId(String instanceId) {
        return instanceDAO.getByInstanceId(instanceId);
    }

    @Override
    public List<Instance> list(InstanceQuery queryParam) {
        return instanceDAO.list(queryParam);
    }

    @Override
    public List<Instance> list(final InstanceQuery queryParam, final long pageIndex, final long pageSize) {
        return instanceDAO.list(queryParam, pageIndex, pageSize);
    }

    @Override
    public long count(final InstanceQuery queryParam) {
        return instanceDAO.count(queryParam);
    }

    @Override
    public List<Instance> listEnabled() {
        return instanceDAO.listEnabled();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveReportData(InstanceOverview overview) {
        InstanceHost host = overview.getHost();
        InstanceHost instanceHost = instanceHostDAO.getByInstanceId(host.getInstanceId());
        if (instanceHost == null) {
            instanceHostDAO.add(host);
        } else {
            instanceHostDAO.update(instanceHost);
        }
        instanceThreadStatisticDAO.add(overview.getThreadStatistic());
        instanceJvmMemoryDAO.addAll(overview.getJvmMemories());
        instanceGarbageCollectorsDAO.addAll(overview.getGarbageCollectors());
    }

    @Override
    public List<InstanceHost> listHostInfo(StatisticQuery query) {
        return instanceHostDAO.list(query);
    }

    @Override
    public InstanceHost getHostInfo(String instanceId) {
        return instanceHostDAO.getByInstanceId(instanceId);
    }

    @Override
    public List<InstanceThreadStatistic> listThreadStatistics(StatisticQuery query) {
        return instanceThreadStatisticDAO.list(query);
    }

    @Override
    public List<InstanceJvmMemory> listJvmMemories(StatisticQuery query) {
        return instanceJvmMemoryDAO.list(query);
    }

    @Override
    public List<InstanceGarbageCollectors> listGarbageCollectors(StatisticQuery query) {
        return instanceGarbageCollectorsDAO.list(query);
    }

    /************************* setter *******************************/

    @Autowired
    public void setInstanceDAO(InstanceDAO instanceDAO) {
        this.instanceDAO = instanceDAO;
    }

    @Autowired
    public void setInstanceHostDAO(InstanceHostDAO instanceHostDAO) {
        this.instanceHostDAO = instanceHostDAO;
    }

    @Autowired
    public void setInstanceThreadStatisticDAO(InstanceThreadStatisticDAO instanceThreadStatisticDAO) {
        this.instanceThreadStatisticDAO = instanceThreadStatisticDAO;
    }

    @Autowired
    public void setInstanceJvmMemoryDAO(InstanceJvmMemoryDAO instanceJvmMemoryDAO) {
        this.instanceJvmMemoryDAO = instanceJvmMemoryDAO;
    }

    @Autowired
    public void setInstanceGarbageCollectorsDAO(InstanceGarbageCollectorsDAO instanceGarbageCollectorsDAO) {
        this.instanceGarbageCollectorsDAO = instanceGarbageCollectorsDAO;
    }
}
