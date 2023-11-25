package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.PageQueryRequest;
import cn.easii.tutelary.bean.domain.query.RoleQuery;

import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.*;
import java.time.*;
import java.math.*;

@Data
@AutoMapper(target = RoleQuery.class, reverseConvertGenerate = false)
@Schema(name = "RolePageQueryRequest", description = "角色表分页查询入参")
public class RolePageQueryRequest extends PageQueryRequest {

    @Schema(name = "roleName", description = "角色名称")
    private String roleName;

    @Schema(name = "enableStatus", description = "启用状态")
    private Integer enableStatus;

}