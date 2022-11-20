package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceThreadStatistic;
import com.tutelary.bean.domain.query.InstanceThreadStatisticQuery;
import com.tutelary.bean.entity.InstanceThreadStatisticEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceThreadStatisticRepository
    extends BaseRepository<InstanceThreadStatisticQuery, InstanceThreadStatistic, InstanceThreadStatisticEntity> {
}
