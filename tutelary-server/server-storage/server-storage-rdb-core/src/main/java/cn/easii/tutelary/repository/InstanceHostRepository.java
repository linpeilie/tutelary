package cn.easii.tutelary.repository;

import cn.easii.tutelary.bean.domain.InstanceHost;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;
import cn.easii.tutelary.bean.entity.InstanceHostEntity;
import cn.easii.tutelary.common.repository.BaseRepository;
import cn.easii.tutelary.dao.InstanceHostDAO;

public interface InstanceHostRepository
    extends BaseRepository<StatisticQuery, InstanceHost, InstanceHostEntity>, InstanceHostDAO {

    InstanceHost getByInstanceId(String instanceId);

    boolean update(InstanceHost instanceHost);

}
