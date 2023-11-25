package cn.easii.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.bean.domain.query.UserQuery;
import cn.easii.tutelary.bean.entity.UserEntity;
import cn.easii.tutelary.common.repository.AbstractRepository;
import cn.easii.tutelary.mapper.UserMapper;
import cn.easii.tutelary.repository.UserRepository;
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
    public User getByUserId(String userId) {
        final LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        return super.getOneForDomain(queryWrapper.eq(UserEntity::getUserId, userId));
    }

    @Override
    public User getByPhoneNumber(final String phoneNumber) {
        final LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        return super.getOneForDomain(queryWrapper.eq(UserEntity::getPhoneNumber, phoneNumber));
    }

    @Override
    public boolean edit(User dto) {
        LambdaUpdateWrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserEntity::getNickName, dto.getNickName());
        updateWrapper.set(UserEntity::getPhoneNumber, dto.getPhoneNumber());
        updateWrapper.set(UserEntity::getRemark, dto.getRemark());
        updateWrapper.eq(UserEntity::getUserId, dto.getUserId());
        return super.update(new UserEntity(), updateWrapper);
    }
}
