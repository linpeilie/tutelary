package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.query.InstanceHostQuery;
import com.tutelary.bean.entity.InstanceHostEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceHostRepository
        extends BaseRepository<InstanceHostQuery, InstanceHost, InstanceHostEntity> {
}
