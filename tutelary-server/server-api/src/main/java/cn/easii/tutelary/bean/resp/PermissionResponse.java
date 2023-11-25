package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AutoMapper(target = Permission.class)
@EqualsAndHashCode(callSuper = true)
public class PermissionResponse extends AbstractResponse {

    @Schema(name = "permissionId", description = "权限ID")
    private String permissionId;

    @Schema(name = "permissionName", description = "权限名称")
    private String permissionName;

    @Schema(name = "parentId", description = "父级权限ID")
    private String parentId;

    @Schema(name = "permissionType", description = "权限类型，【M-菜单；R-资源】")
    private String permissionType;

    @Schema(name = "enableStatus", description = "启用状态【1-已启用；0-未启用】")
    private Integer enableStatus;

    @Schema(name = "identification", description = "权限标识")
    private String identification;

    @Schema(name = "remark", description = "备注")
    private String remark;

}
