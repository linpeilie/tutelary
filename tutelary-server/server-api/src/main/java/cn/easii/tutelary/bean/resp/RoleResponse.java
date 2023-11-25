package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import io.github.linpeilie.annotations.AutoMapper;
import cn.easii.tutelary.bean.domain.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.*;
import java.time.*;

@Data
@Schema(name = "RoleResponse", description = "角色表返回参数")
@AutoMapper(target = Role.class, convertGenerate = false)
public class RoleResponse extends AbstractResponse {


    @Schema(name = "id", description = "")
    private Long id;

    @Schema(name = "roleId", description = "角色ID")
    private String roleId;

    @Schema(name = "roleName", description = "角色名称")
    private String roleName;

    @Schema(name = "enableStatus", description = "启用状态")
    private Integer enableStatus;

    @Schema(name = "remark", description = "备注")
    private String remark;

    @Schema(name = "createUserId", description = "创建用户")
    private String createUserId;

    @Schema(name = "updateUserId", description = "修改用户ID")
    private String updateUserId;

    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

}