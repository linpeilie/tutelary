package com.tutelary.repository.impl;

import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.bean.entity.InstanceGarbageCollectorsEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceGarbageCollectorsMapper;
import com.tutelary.repository.InstanceGarbageCollectorsRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceGarbageCollectorsRepositoryImpl
    extends AbstractRepository<StatisticQuery, InstanceGarbageCollectors, InstanceGarbageCollectorsEntity, InstanceGarbageCollectorsMapper>
    implements InstanceGarbageCollectorsRepository {

}
