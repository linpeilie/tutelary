package com.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tutelary.bean.domain.User;
import com.tutelary.bean.domain.query.UserQuery;
import com.tutelary.bean.entity.UserEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.UserMapper;
import com.tutelary.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepository<UserQuery, User, UserEntity, UserMapper>
    implements UserRepository {
    @Override
    public User getByUsername(final String username) {
        final LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        return super.getOneForDomain(queryWrapper.eq(UserEntity::getUsername, username));
    }

    @Override
    public User getByPhoneNumber(final String phoneNumber) {
        final LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        return super.getOneForDomain(queryWrapper.eq(UserEntity::getPhoneNumber, phoneNumber));
    }

    @Override
    public void edit(final User user) {
        // TODO:
    }
}
