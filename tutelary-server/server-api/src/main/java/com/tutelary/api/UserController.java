package com.tutelary.api;

import com.tutelary.bean.domain.User;
import com.tutelary.bean.domain.query.UserQuery;
import com.tutelary.bean.req.UserAddRequest;
import com.tutelary.bean.req.UserEditRequest;
import com.tutelary.bean.req.UserPageQueryRequest;
import com.tutelary.bean.resp.UserInfoResponse;
import com.tutelary.common.bean.R;
import com.tutelary.common.bean.resp.PageResult;
import com.tutelary.service.UserService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "UserController", description = "用户信息相关接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Converter converter;

    @PostMapping(value = "add")
    @Operation(summary = "新增", description = "新增用户信息")
    @ApiResponse(description = "返回新增是否成功", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public R<Void> add(@RequestBody @Validated UserAddRequest userAddRequest) {
        User user = converter.convert(userAddRequest, User.class);
        userService.add(user);
        return R.success();
    }

    @PostMapping(value = "edit")
    @Operation(summary = "编辑", description = "编辑用户信息")
    @ApiResponse(description = "返回编辑是否成功", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public R<Void> edit(@RequestBody @Validated UserEditRequest userEditRequest) {
        User user = converter.convert(userEditRequest, User.class);
        userService.edit(user);
        return R.success();
    }

    @PostMapping(value = "pageQuery")
    @Operation(summary = "分页查询", description = "用户信息分页查询")
    @ApiResponse(description = "返回分页查询结果",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInfoResponse.class)))
    public R<PageResult<UserInfoResponse>> pageQuery(@RequestBody @Validated UserPageQueryRequest userPageQueryRequest) {
        UserQuery userQuery = converter.convert(userPageQueryRequest, UserQuery.class);
        long count = userService.count(userQuery);
        if (count == 0) {
            return R.success(PageResult.empty());
        }
        List<User> list = userService.list(userQuery, userPageQueryRequest.getPageIndex(), userPageQueryRequest.getPageSize());
        return R.success(new PageResult<>(count, converter.convert(list, UserInfoResponse.class)));
    }

}