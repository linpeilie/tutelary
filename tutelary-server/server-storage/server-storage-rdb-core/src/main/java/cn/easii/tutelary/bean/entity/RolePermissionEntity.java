package cn.easii.tutelary.bean.entity;

import cn.easii.tutelary.bean.domain.RolePermission;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Index;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

@Data
@AutoMapper(target = RolePermission.class)
@TableName(value = "role_permission")
@Table(comment = "角色权限关联表", indexs = {
    @Index(indexName = "idx_role_permission", unique = true, columns = {"role_id", "permission_id"})
})
public class RolePermissionEntity {

    @Column(isNull = false, length = 32, comment = "角色ID", sequence = 1)
    private String roleId;

    @Column(isNull = false, length = 32, comment = "权限ID", sequence = 2)
    private String permissionId;

}
