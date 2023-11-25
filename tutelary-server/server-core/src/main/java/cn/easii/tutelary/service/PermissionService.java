package cn.easii.tutelary.service;

import cn.easii.tutelary.bean.domain.Permission;
import java.util.List;

public interface PermissionService {

    List<Permission> selectPermissionsByUserId(String userId);

    void add(Permission permission);

}
