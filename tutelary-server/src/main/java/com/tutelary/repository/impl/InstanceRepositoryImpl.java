package com.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tutelary.bean.converter.InstanceConverter;
import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.common.enums.InstanceStateEnum;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceMapper;
import com.tutelary.repository.InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceRepositoryImpl
        extends AbstractRepository<InstanceQuery, Instance, InstanceEntity, InstanceMapper>
        implements InstanceRepository {

    @Autowired
    public InstanceRepositoryImpl(InstanceConverter converter) {
        super(converter);
    }

    @Override
    public Instance getByInstanceId(String instanceId) {
        LambdaQueryWrapper<InstanceEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(InstanceEntity::getInstanceId, instanceId);
        return converter.entityToDomain(super.getOne(queryWrapper));
    }

    @Override
    public boolean del(String instanceId) {
        return super.remove(Wrappers.lambdaQuery(InstanceEntity.class).eq(InstanceEntity::getInstanceId, instanceId));
    }

    @Override
    public boolean validedInstance(String instanceId) {
        LambdaUpdateWrapper<InstanceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(InstanceEntity::getInstanceId, instanceId);
        updateWrapper.set(InstanceEntity::getState, InstanceStateEnum.VALID);
        return super.update(new InstanceEntity(), updateWrapper);
    }

    @Override
    public boolean invalidedInstance(String instanceId) {
        LambdaUpdateWrapper<InstanceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(InstanceEntity::getInstanceId, instanceId);
        updateWrapper.set(InstanceEntity::getState, InstanceStateEnum.INVALID);
        return super.update(new InstanceEntity(), updateWrapper);
    }
}
