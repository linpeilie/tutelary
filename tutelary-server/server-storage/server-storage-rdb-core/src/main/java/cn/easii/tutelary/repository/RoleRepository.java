package cn.easii.tutelary.repository;

import cn.easii.tutelary.bean.domain.Role;
import cn.easii.tutelary.bean.domain.query.RoleQuery;
import cn.easii.tutelary.bean.entity.RoleEntity;
import cn.easii.tutelary.dao.RoleDAO;
import cn.easii.tutelary.common.repository.BaseRepository;
import java.util.List;

/**
* <p>
* 角色表 数据操作层
* </p>
*
* @author linpeilie
* @since 2024-01-14 18:10:11
*/
public interface RoleRepository extends BaseRepository<RoleQuery, Role, RoleEntity>, RoleDAO {

    boolean add(Role role);

    boolean edit(Role role);

    List<Role> list(RoleQuery roleQuery, long pageIndex, long pageSize);

    List<Role> list(RoleQuery roleQuery);

    long count(RoleQuery roleQuery);

}