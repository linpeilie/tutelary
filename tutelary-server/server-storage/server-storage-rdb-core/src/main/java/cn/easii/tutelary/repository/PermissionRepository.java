package cn.easii.tutelary.repository;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.bean.domain.query.PermissionQuery;
import cn.easii.tutelary.bean.entity.PermissionEntity;
import cn.easii.tutelary.common.repository.BaseRepository;
import cn.easii.tutelary.dao.PermissionDAO;
import java.util.List;

public interface PermissionRepository
    extends BaseRepository<PermissionQuery, Permission, PermissionEntity>, PermissionDAO {

    List<Permission> selectPermissionsByUserId(String userId);

}
