package cn.easii.tutelary.api;

import cn.easii.tutelary.bean.req.UserAddRequest;
import cn.easii.tutelary.bean.req.UserEditRequest;
import cn.easii.tutelary.bean.req.UserPageQueryRequest;
import cn.easii.tutelary.bean.resp.PermissionResponse;
import cn.easii.tutelary.bean.resp.UserInfoResponse;
import cn.easii.tutelary.common.bean.R;
import cn.easii.tutelary.common.bean.resp.PageResult;
import cn.easii.tutelary.service.PermissionService;
import cn.easii.tutelary.service.UserService;
import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.bean.domain.query.UserQuery;
import cn.easii.tutelary.utils.AuthHelper;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户信息相关接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PermissionService permissionService;
    private final Converter converter;

    @PostMapping(value = "info")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户信息")
    @ApiResponse(description = "返回用户信息", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = UserInfoResponse.class)))
    public R<UserInfoResponse> userInfo() {
        String userId = AuthHelper.getUserId();
        return R.success(converter.convert(userService.loadUserByUserId(userId), UserInfoResponse.class));
    }

    @PostMapping(value = "permissions")
    public R<List<PermissionResponse>> userPermissions() {
        String userId = AuthHelper.getUserId();
        return R.success(
            converter.convert(permissionService.selectPermissionsByUserId(userId), PermissionResponse.class));
    }

    @PostMapping(value = "add")
    @Operation(summary = "新增", description = "新增用户信息")
    @ApiResponse(description = "返回新增是否成功", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public R<Void> add(@RequestBody UserAddRequest userAddRequest) {
        User user = converter.convert(userAddRequest, User.class);
        userService.add(user);
        return R.success();
    }

    @PostMapping(value = "edit")
    @Operation(summary = "编辑", description = "编辑用户信息")
    @ApiResponse(description = "返回编辑是否成功", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public R<Void> edit(@RequestBody UserEditRequest userEditRequest) {
        User user = converter.convert(userEditRequest, User.class);
        userService.edit(user);
        return R.success();
    }

    @PostMapping(value = "pageQuery")
    @Operation(summary = "分页查询", description = "用户信息分页查询")
    @ApiResponse(description = "返回分页查询结果", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInfoResponse.class)))
    public R<PageResult<UserInfoResponse>> pageQuery(@RequestBody UserPageQueryRequest userPageQueryRequest) {
        UserQuery userQuery = converter.convert(userPageQueryRequest, UserQuery.class);
        long count = userService.count(userQuery);
        if (count == 0) {
            return R.success(PageResult.empty());
        }
        List<User> list =
            userService.list(userQuery, userPageQueryRequest.getPageIndex(), userPageQueryRequest.getPageSize());
        return R.success(new PageResult<>(count, converter.convert(list, UserInfoResponse.class)));
    }

}