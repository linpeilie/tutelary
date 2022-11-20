package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.domain.query.InstanceJvmMemoryQuery;
import com.tutelary.bean.entity.InstanceJvmMemoryEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceJvmMemoryRepository
    extends BaseRepository<InstanceJvmMemoryQuery, InstanceJvmMemory, InstanceJvmMemoryEntity> {
}
