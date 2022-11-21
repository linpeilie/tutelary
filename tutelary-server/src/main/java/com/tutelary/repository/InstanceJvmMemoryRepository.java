package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.domain.query.OverviewQuery;
import com.tutelary.bean.entity.InstanceJvmMemoryEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceJvmMemoryRepository
    extends BaseRepository<OverviewQuery, InstanceJvmMemory, InstanceJvmMemoryEntity> {
}
