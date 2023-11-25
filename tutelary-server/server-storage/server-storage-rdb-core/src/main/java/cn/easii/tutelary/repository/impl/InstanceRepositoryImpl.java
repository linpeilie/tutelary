package cn.easii.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import cn.easii.tutelary.bean.entity.InstanceEntity;
import cn.easii.tutelary.common.repository.AbstractRepository;
import cn.easii.tutelary.mapper.InstanceMapper;
import cn.easii.tutelary.repository.InstanceRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceRepositoryImpl
    extends AbstractRepository<InstanceQuery, Instance, InstanceEntity, InstanceMapper>
    implements InstanceRepository {

    @Override
    public Instance getByInstanceId(String instanceId) {
        LambdaQueryWrapper<InstanceEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(InstanceEntity::getInstanceId, instanceId);
        return entityToDomain(super.getOne(queryWrapper));
    }

    @Override
    public boolean del(String instanceId) {
        return super.remove(Wrappers.lambdaQuery(InstanceEntity.class).eq(InstanceEntity::getInstanceId, instanceId));
    }

    @Override
    public boolean validedInstance(String instanceId) {
        LambdaUpdateWrapper<InstanceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(InstanceEntity::getInstanceId, instanceId);
        return super.update(new InstanceEntity(), updateWrapper);
    }

    @Override
    public boolean invalidedInstance(String instanceId) {
        LambdaUpdateWrapper<InstanceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(InstanceEntity::getInstanceId, instanceId);
        return super.update(new InstanceEntity(), updateWrapper);
    }

    @Override
    public List<Instance> listEnabled() {
        LambdaQueryWrapper<InstanceEntity> queryWrapper = Wrappers.lambdaQuery();
        return entitiesToDomainList(super.list(queryWrapper));
    }
}
