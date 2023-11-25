package cn.easii.tutelary.bean.entity;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Permission.class)
@TableName(value = "permission")
@Table(comment = "权限表")
public class PermissionEntity extends BaseEntity {

    @Column(isNull = false, length = 32, comment = "权限ID", sequence = 2)
    private String permissionId;

    @Column(isNull = false, length = 64, comment = "权限名称", sequence = 3)
    private String permissionName;

    @Column(isNull = false, length = 32, comment = "父级权限ID", sequence = 4)
    private String parentId;

    @Column(isNull = false, length = 4, comment = "权限类型（M菜单 R资源）", sequence = 5)
    private String permissionType;

    @Column(isNull = false, length = 1, comment = "启用状态（1启用 0未启用）", sequence = 6)
    private Integer enableStatus;

    @Column(length = 100, comment = "权限标识", sequence = 7)
    private String identification;

    @Column(length = 500, comment = "备注", sequence = 8)
    private String remark;

}
