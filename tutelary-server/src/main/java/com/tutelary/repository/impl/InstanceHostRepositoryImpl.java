package com.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tutelary.bean.converter.InstanceHostConverter;
import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.bean.entity.InstanceHostEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceHostMapper;
import com.tutelary.repository.InstanceHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceHostRepositoryImpl
    extends AbstractRepository<StatisticQuery, InstanceHost, InstanceHostEntity, InstanceHostMapper>
    implements InstanceHostRepository {

    @Autowired
    public InstanceHostRepositoryImpl(InstanceHostConverter converter) {
        super(converter);
    }

    @Override
    public InstanceHost getByInstanceId(String instanceId) {
        LambdaQueryWrapper<InstanceHostEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(InstanceHostEntity::getInstanceId, instanceId);
        return converter.entityToDomain(super.getOne(queryWrapper));
    }

    @Override
    public boolean update(InstanceHost instanceHost) {
        LambdaUpdateWrapper<InstanceHostEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(InstanceHostEntity::getInstanceId, instanceHost.getInstanceId());
        return super.update(converter.domainToEntity(instanceHost), updateWrapper);
    }
}
