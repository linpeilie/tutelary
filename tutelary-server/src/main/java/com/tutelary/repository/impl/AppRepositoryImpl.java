package com.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tutelary.bean.converter.AppConverter;
import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.AppMapper;
import com.tutelary.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppRepositoryImpl extends AbstractRepository<AppQuery, App, AppEntity, AppMapper> implements AppRepository {

    @Autowired
    public AppRepositoryImpl(AppConverter converter) {
        super(converter);
    }

    @Override
    public App getByName(String appName) {
        LambdaQueryWrapper<AppEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AppEntity::getAppName, appName);
        return converter.entityToDomain(super.getOne(queryWrapper));
    }

    @Override
    public boolean addInstance(String appName) {
        LambdaUpdateWrapper<AppEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.setSql("instance_num = instance_num + 1");
        updateWrapper.eq(AppEntity::getAppName, appName);
        return super.update(new AppEntity(), updateWrapper);
    }

    @Override
    public boolean removeInstance(String appName) {
        LambdaUpdateWrapper<AppEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.setSql("instance_num = instance_num - 1");
        updateWrapper.eq(AppEntity::getAppName, appName);
        return super.update(new AppEntity(), updateWrapper);
    }
}
