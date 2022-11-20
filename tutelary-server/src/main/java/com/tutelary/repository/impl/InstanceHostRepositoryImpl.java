package com.tutelary.repository.impl;

import com.tutelary.bean.converter.InstanceHostConverter;
import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.query.InstanceHostQuery;
import com.tutelary.bean.entity.InstanceHostEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceHostMapper;
import com.tutelary.repository.InstanceHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceHostRepositoryImpl
        extends AbstractRepository<InstanceHostQuery, InstanceHost, InstanceHostEntity, InstanceHostMapper>
        implements InstanceHostRepository {

    @Autowired
    public InstanceHostRepositoryImpl(InstanceHostConverter converter) {
        super(converter);
    }

}
