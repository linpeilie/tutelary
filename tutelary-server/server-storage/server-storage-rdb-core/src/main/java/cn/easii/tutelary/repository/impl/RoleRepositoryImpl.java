package cn.easii.tutelary.repository.impl;

import cn.easii.tutelary.bean.domain.Role;
import cn.easii.tutelary.bean.domain.query.RoleQuery;
import cn.easii.tutelary.repository.RoleRepository;
import cn.easii.tutelary.bean.entity.RoleEntity;
import cn.easii.tutelary.mapper.RoleMapper;
import cn.easii.tutelary.common.repository.AbstractRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import io.github.linpeilie.Converter;
import java.util.List;
import cn.easii.tutelary.bean.domain.Role;

/**
* <p>
* 角色表 数据操作层
* </p>
*
* @author linpeilie
* @since 2024-01-14 18:10:11
*/
@Repository
public class RoleRepositoryImpl  extends AbstractRepository<RoleQuery, Role, RoleEntity, RoleMapper>
    implements RoleRepository {

    @Override
    public boolean edit(Role role) {
        LambdaUpdateWrapper<RoleEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(RoleEntity::getEnableStatus, role.getEnableStatus());
        updateWrapper.set(RoleEntity::getRemark, role.getRemark());
        updateWrapper.eq(RoleEntity::getRoleId, role.getRoleId());
        return super.update(new RoleEntity(), updateWrapper);
    }

    @Override
    public Role findByRoleName(String roleName) {
        LambdaQueryWrapper<RoleEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleEntity::getRoleName, roleName);
        return super.getOneForDomain(queryWrapper);
    }
}