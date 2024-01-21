package cn.easii.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.easii.tutelary.bean.domain.App;
import cn.easii.tutelary.bean.domain.query.AppQuery;
import cn.easii.tutelary.bean.entity.AppEntity;
import cn.easii.tutelary.common.repository.AbstractRepository;
import cn.easii.tutelary.mapper.AppMapper;
import cn.easii.tutelary.repository.AppRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AppRepositoryImpl extends AbstractRepository<AppQuery, App, AppEntity, AppMapper>
    implements AppRepository {

    @Override
    public App getByName(String appName) {
        LambdaQueryWrapper<AppEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AppEntity::getAppName, appName);
        return entityToDomain(super.getOne(queryWrapper));
    }
}
