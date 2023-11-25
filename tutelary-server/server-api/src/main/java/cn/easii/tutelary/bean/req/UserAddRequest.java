package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.AbstractRequest;
import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.common.enums.UserStateEnum;
import cn.easii.tutelary.common.utils.ParameterUtils;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = User.class)
@Schema(name = "UserAddRequest", description = "用户新增入参")
public class UserAddRequest extends AbstractRequest {

    @Schema(name = "username", description = "用户名")
    private String username;

    @Schema(name = "nickName", description = "昵称")
    private String nickName;

    @Schema(name = "phoneNumber", description = "手机号")
    private String phoneNumber;

    @Schema(name = "password", description = "密码")
    private String password;

    @Schema(name = "remark", description = "备注")
    private String remark;

    @Override
    public void checkInput() {
        super.checkInput();
        ParameterUtils.notBlank(username, "用户账号不能为空");
        ParameterUtils.notBlank(nickName, "用户昵称不能为空");
        ParameterUtils.notBlank(phoneNumber, "手机号不能为空");
        ParameterUtils.notBlank(password, "密码不能为空");
    }
}
