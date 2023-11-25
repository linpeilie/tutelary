package cn.easii.tutelary.dao;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.bean.domain.query.PermissionQuery;
import cn.easii.tutelary.dao.common.QueryDAO;
import java.util.List;

public interface PermissionDAO extends QueryDAO<PermissionQuery, Permission> {

    List<Permission> selectPermissionsByUserId(String userId);

}
