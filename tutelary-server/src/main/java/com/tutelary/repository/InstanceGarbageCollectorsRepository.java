package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.query.InstanceGarbageCollectorsQuery;
import com.tutelary.bean.domain.query.InstanceHostQuery;
import com.tutelary.bean.entity.InstanceGarbageCollectorsEntity;
import com.tutelary.bean.entity.InstanceHostEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceGarbageCollectorsRepository
        extends BaseRepository<InstanceGarbageCollectorsQuery, InstanceGarbageCollectors, InstanceGarbageCollectorsEntity> {
}
