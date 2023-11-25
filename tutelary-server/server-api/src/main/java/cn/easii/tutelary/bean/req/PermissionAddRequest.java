package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.bean.domain.Permission;
import cn.easii.tutelary.common.bean.req.AbstractRequest;
import cn.easii.tutelary.common.utils.ParameterUtils;
import cn.easii.tutelary.enums.EnableStatusEnum;
import cn.easii.tutelary.enums.PermissionTypeEnum;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Permission.class)
public class PermissionAddRequest extends AbstractRequest {

    private String permissionName;

    private String parentId;

    private String permissionType;

    private Integer enableStatus;

    private String identification;

    private String remark;

    @Override
    public void checkInput() {
        super.checkInput();
        ParameterUtils.notBlank(permissionName, "权限名称不能为空");
        ParameterUtils.notBlank(permissionType, "权限类型必选");
        ParameterUtils.nonNull(PermissionTypeEnum.getByType(permissionType), "未知权限类型");
        ParameterUtils.nonNull(enableStatus, "启用状态必选");
        ParameterUtils.nonNull(EnableStatusEnum.getByStatus(enableStatus), "未知的启用状态");
        ParameterUtils.notBlank(identification, "权限标识不能为空");
    }
}
