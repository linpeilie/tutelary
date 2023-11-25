package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.AbstractRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;
import java.math.*;
import java.time.*;
import cn.easii.tutelary.bean.domain.Role;

@Data
@Schema(name = "RoleEditRequest", description = "角色表编辑入参")
@AutoMapper(target = Role.class, reverseConvertGenerate = false)
public class RoleEditRequest extends AbstractRequest {

    @Schema(name = "roleId", description = "角色ID")
    private String roleId;

    @Schema(name = "roleName", description = "角色名称")
    private String roleName;

    @Schema(name = "enableStatus", description = "启用状态")
    private Integer enableStatus;

    @Schema(name = "remark", description = "备注")
    private String remark;

}