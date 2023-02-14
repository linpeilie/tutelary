package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.bean.entity.InstanceGarbageCollectorsEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceGarbageCollectorsRepository
    extends BaseRepository<StatisticQuery, InstanceGarbageCollectors, InstanceGarbageCollectorsEntity> {
}
