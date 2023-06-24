package com.tutelary.bean.req;

import com.tutelary.common.bean.req.AbstractRequest;
import com.tutelary.common.utils.ParameterUtils;
import lombok.Data;

@Data
public class AuthenticationRequest extends AbstractRequest {

    private String username;

    private String password;

    @Override
    public void checkInput() {
        super.checkInput();
        ParameterUtils.notBlank(username, "用户名不能为空");
        ParameterUtils.notBlank(password, "密码不能为空");
    }
}
