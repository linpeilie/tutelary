package com.tutelary.repository.impl;

import com.tutelary.bean.converter.InstanceThreadStatisticConverter;
import com.tutelary.bean.domain.InstanceThreadStatistic;
import com.tutelary.bean.domain.query.InstanceThreadStatisticQuery;
import com.tutelary.bean.entity.InstanceThreadStatisticEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceThreadStatisticMapper;
import com.tutelary.repository.InstanceThreadStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceThreadStatisticRepositoryImpl
        extends AbstractRepository<InstanceThreadStatisticQuery, InstanceThreadStatistic, InstanceThreadStatisticEntity, InstanceThreadStatisticMapper>
        implements InstanceThreadStatisticRepository {

    @Autowired
    public InstanceThreadStatisticRepositoryImpl(InstanceThreadStatisticConverter converter) {
        super(converter);
    }

}
