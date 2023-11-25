package cn.easii.tutelary.repository.impl;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.bean.domain.query.PermissionQuery;
import cn.easii.tutelary.bean.entity.PermissionEntity;
import cn.easii.tutelary.common.repository.AbstractRepository;
import cn.easii.tutelary.mapper.PermissionMapper;
import cn.easii.tutelary.repository.PermissionRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryImpl
    extends AbstractRepository<PermissionQuery, Permission, PermissionEntity, PermissionMapper>
    implements PermissionRepository {

    @Override
    public List<Permission> selectPermissionsByUserId(String userId) {
        return entitiesToDomainList(getBaseMapper().selectPermissionsByUserId(userId));
    }

}
