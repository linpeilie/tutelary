package com.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tutelary.bean.converter.InstanceConverter;
import com.tutelary.bean.dto.InstanceDTO;
import com.tutelary.bean.dto.InstanceQueryDTO;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.common.converter.EntityDtoConverter;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceMapper;
import com.tutelary.repository.InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceRepositoryImpl
    extends AbstractRepository<InstanceQueryDTO, InstanceDTO, InstanceEntity, InstanceMapper>
    implements InstanceRepository {

    @Autowired
    public InstanceRepositoryImpl(InstanceConverter converter) {
        super(converter);
    }

    @Override
    public InstanceDTO getByInstanceId(String instanceId) {
        LambdaQueryWrapper<InstanceEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(InstanceEntity::getInstanceId, instanceId);
        return converter.entityToDto(super.getOne(queryWrapper));
    }
}
