package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.AbstractRequest;
import cn.easii.tutelary.common.utils.ParameterUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "LoginRequest", description = "登录信息")
public class LoginRequest extends AbstractRequest {

    @Schema(name = "username", description = "用户账号")
    private String username;

    @Schema(name = "password", description = "密码")
    private String password;

    @Override
    public void checkInput() {
        super.checkInput();
        ParameterUtils.notBlank(username, "账号不能为空");
        ParameterUtils.notBlank(password, "密码不能为空");
    }
}
