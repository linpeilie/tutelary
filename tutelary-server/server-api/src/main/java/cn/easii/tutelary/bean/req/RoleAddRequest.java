package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.AbstractRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import java.math.*;
import java.time.*;

import cn.easii.tutelary.bean.domain.Role;

@Data
@Schema(name = "RoleAddRequest", description = "角色表新增入参}")
@AutoMapper(target = Role.class)
public class RoleAddRequest extends AbstractRequest {


    @Schema(name = "roleName", description = "角色名称")
    private String roleName;

    @Schema(name = "enableStatus", description = "启用状态")
    private Integer enableStatus;

    @Schema(name = "remark", description = "备注")
    private String remark;

}