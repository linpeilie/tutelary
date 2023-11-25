package cn.easii.tutelary.api;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.bean.req.PermissionAddRequest;
import cn.easii.tutelary.common.bean.R;
import cn.easii.tutelary.service.PermissionService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@Tag(name = "PermissionController", description = "权限相关接口")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    private final Converter converter;

    @PostMapping(value = "add")
    public R<Void> add(@RequestBody PermissionAddRequest permissionAddRequest) {
        Permission permission = converter.convert(permissionAddRequest, Permission.class);
        permissionService.add(permission);
        return R.success();
    }

}
