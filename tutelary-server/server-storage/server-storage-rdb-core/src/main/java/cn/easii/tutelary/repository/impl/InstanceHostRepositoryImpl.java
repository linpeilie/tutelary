package cn.easii.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.easii.tutelary.bean.domain.InstanceHost;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;
import cn.easii.tutelary.bean.entity.InstanceHostEntity;
import cn.easii.tutelary.common.repository.AbstractRepository;
import cn.easii.tutelary.mapper.InstanceHostMapper;
import cn.easii.tutelary.repository.InstanceHostRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceHostRepositoryImpl
    extends AbstractRepository<StatisticQuery, InstanceHost, InstanceHostEntity, InstanceHostMapper>
    implements InstanceHostRepository {

    @Override
    public InstanceHost getByInstanceId(String instanceId) {
        LambdaQueryWrapper<InstanceHostEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(InstanceHostEntity::getInstanceId, instanceId);
        return entityToDomain(super.getOne(queryWrapper));
    }

    @Override
    public boolean update(InstanceHost instanceHost) {
        LambdaUpdateWrapper<InstanceHostEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(InstanceHostEntity::getInstanceId, instanceHost.getInstanceId());
        return super.update(domainToEntity(instanceHost), updateWrapper);
    }
}
