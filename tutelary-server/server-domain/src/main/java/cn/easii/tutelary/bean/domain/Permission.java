package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.enums.EnableStatusEnum;
import cn.easii.tutelary.enums.PermissionTypeEnum;
import lombok.Data;

@Data
public class Permission extends BaseDomain {

    /**
     * 权限ID
     */
    private String permissionId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 父级权限ID
     */
    private String parentId;

    /**
     * 权限类型
     * {@link PermissionTypeEnum}
     */
    private String permissionType;

    /**
     * 启用状态
     * {@link EnableStatusEnum}
     */
    private Integer enableStatus;

    /**
     * 权限标识
     */
    private String identification;

    /**
     * 备注
     */
    private String remark;

}
