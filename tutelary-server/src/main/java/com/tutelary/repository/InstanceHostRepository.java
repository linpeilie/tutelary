package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.bean.entity.InstanceHostEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceHostRepository
    extends BaseRepository<StatisticQuery, InstanceHost, InstanceHostEntity> {

    InstanceHost getByInstanceId(String instanceId);

    boolean update(InstanceHost instanceHost);

}
