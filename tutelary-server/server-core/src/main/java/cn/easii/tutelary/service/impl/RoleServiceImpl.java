package cn.easii.tutelary.service.impl;

import cn.easii.tutelary.common.constants.SystemResponseCode;
import cn.easii.tutelary.common.utils.Asserts;
import cn.easii.tutelary.service.RoleService;
import cn.easii.tutelary.bean.domain.Role;
import cn.easii.tutelary.bean.domain.query.RoleQuery;
import cn.easii.tutelary.utils.IdGeneratorHelper;
import org.springframework.stereotype.Service;
import cn.easii.tutelary.dao.RoleDAO;
import javax.annotation.Resource;
import java.util.List;

/**
* <p>
* 角色表 服务实现类
* </p>
*
* @author linpeilie
* @since 2024-01-14 17:20:40
*/
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDAO roleDAO;

    @Override
    public void add(Role role) {
        // 校验重复、生成序列号
        Role oldRole = roleDAO.findByRoleName(role.getRoleName());
        Asserts.isNull(oldRole, SystemResponseCode.ROLE_EXISTED, role.getRoleName());

        String roleId = IdGeneratorHelper.getId();
        role.setRoleId(roleId);
        roleDAO.add(role);
    }

    @Override
    public void edit(Role role) {
        roleDAO.edit(role);
    }

    @Override
    public List<Role> list(RoleQuery roleQuery, long pageIndex, long pageSize) {
        return roleDAO.list(roleQuery, pageIndex, pageSize);
    }

    @Override
    public List<Role> list(RoleQuery roleQuery) {
        return roleDAO.list(roleQuery);
    }

    @Override
    public long count(RoleQuery roleQuery) {
        return roleDAO.count(roleQuery);
    }

}