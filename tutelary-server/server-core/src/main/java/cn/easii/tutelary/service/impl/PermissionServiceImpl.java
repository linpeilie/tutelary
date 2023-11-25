package cn.easii.tutelary.service.impl;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.dao.PermissionDAO;
import cn.easii.tutelary.service.PermissionService;
import cn.easii.tutelary.utils.IdGeneratorHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionDAO permissionDAO;

    @Override
    public List<Permission> selectPermissionsByUserId(String userId) {
        return permissionDAO.selectPermissionsByUserId(userId);
    }

    @Override
    public void add(Permission permission) {
        // 初始化ID
        permission.setPermissionId(IdGeneratorHelper.getId());
        permissionDAO.add(permission);
    }
}
