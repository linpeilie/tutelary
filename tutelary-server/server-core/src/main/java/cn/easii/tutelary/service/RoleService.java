package cn.easii.tutelary.service;

import cn.easii.tutelary.bean.domain.Role;
import cn.easii.tutelary.bean.domain.query.RoleQuery;
import java.util.List;

/**
* <p>
* 角色表 服务类
* </p>
*
* @author linpeilie
* @since 2024-01-14 17:20:40
*/
public interface RoleService  {
    void add(Role role);

    void edit(Role role);

    List<Role> list(RoleQuery roleQuery, long pageIndex, long pageSize);

    List<Role> list(RoleQuery roleQuery);

    long count(RoleQuery roleQuery);

}
