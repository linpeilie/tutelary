package com.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutelary.bean.converter.AppConverter;
import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.AppQueryDTO;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;
import com.tutelary.common.helper.MybatisPlusQueryHelper;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.AppMapper;
import com.tutelary.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AppRepositoryImpl extends AbstractRepository<AppQueryDTO, AppDTO, AppEntity, AppMapper> implements AppRepository {

    @Autowired
    public AppRepositoryImpl(AppConverter converter) {
        super(converter);
    }

    @Override
    public AppDTO getByName(String appName) {
        LambdaQueryWrapper<AppEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AppEntity::getAppName, appName);
        return converter.entityToDto(super.getOne(queryWrapper));
    }

    @Override
    public boolean addInstance(String appName) {
        LambdaUpdateWrapper<AppEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.setSql("instance_num = instance_num + 1");
        updateWrapper.eq(AppEntity::getAppName, appName);
        return super.update(new AppEntity(), updateWrapper);
    }
}
