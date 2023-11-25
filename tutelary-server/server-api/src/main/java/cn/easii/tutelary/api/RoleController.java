package cn.easii.tutelary.api;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import cn.easii.tutelary.bean.req.RoleAddRequest;
import cn.easii.tutelary.bean.req.RoleEditRequest;
import cn.easii.tutelary.service.RoleService;
import cn.easii.tutelary.bean.req.RolePageQueryRequest;
import cn.easii.tutelary.bean.resp.RoleResponse;
import cn.easii.tutelary.bean.domain.Role;
import cn.easii.tutelary.bean.domain.query.RoleQuery;
import cn.easii.tutelary.common.bean.resp.PageResult;
import cn.easii.tutelary.common.bean.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.linpeilie.Converter;
import java.util.List;
import lombok.AllArgsConstructor;

/**
* <p>
* 角色表 前端控制器
* </p>
*
* @author linpeilie
* @since 2024-01-14 17:20:40
*/
@RestController
@RequestMapping("/role")
@Tag(name = "RoleController", description = "角色表相关接口}")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final Converter converter;

    @PostMapping(value = "add")
    @Operation(summary = "新增", description = "新增角色表")
    @ApiResponse(description = "返回新增是否成功", content = @Content(mediaType = "application/json"))
    public R<Void> add(@RequestBody @Validated RoleAddRequest roleAddRequest) {
        Role role = converter.convert(roleAddRequest, Role.class);
        roleService.add(role);
        return R.success();
    }

    @PostMapping(value = "edit")
    @Operation(summary = "编辑", description = "编辑角色表")
    @ApiResponse(description = "返回编辑是否成功", content = @Content(mediaType = "application/json"))
    public R<Void> edit(@RequestBody @Validated RoleEditRequest roleEditRequest) {
        Role role = converter.convert(roleEditRequest, Role.class);
        roleService.edit(role);
        return R.success();
    }

    @PostMapping(value = "pageQuery")
    @Operation(summary = "分页查询", description = "角色表分页查询")
    @ApiResponse(description = "返回分页查询结果",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class)))
    public R<PageResult<RoleResponse>> pageQuery(@RequestBody @Validated RolePageQueryRequest rolePageQueryRequest) {
        RoleQuery roleQuery = converter.convert(rolePageQueryRequest, RoleQuery.class);
        long count = roleService.count(roleQuery);
        if (count == 0) {
            return R.success(PageResult.empty());
        }
        List<Role> list = roleService.list(roleQuery, rolePageQueryRequest.getPageIndex(), rolePageQueryRequest.getPageSize());
        return R.success(new PageResult<>(count, converter.convert(list, RoleResponse.class)));
    }

}