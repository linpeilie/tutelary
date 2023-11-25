package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "LoginResponse", description = "登录返回的验证信息")
public class LoginResponse extends AbstractResponse {

    @Schema(name = "token", description = "当前登录会话token")
    private String token;

}
