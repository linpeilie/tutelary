package cn.easii.tutelary.dao;

import cn.easii.tutelary.bean.domain.Role;
import cn.easii.tutelary.bean.domain.query.RoleQuery;
import cn.easii.tutelary.dao.common.QueryDAO;
import java.util.List;

/**
* <p>
* 角色表 数据操作层
* </p>
*
* @author linpeilie
* @since 2024-01-14 17:20:40
*/
public interface RoleDAO extends QueryDAO<RoleQuery, Role>  {

    Role findByRoleName(String roleName);

}
