package com.tutelary.bean.req;

import com.tutelary.bean.domain.User;
import com.tutelary.common.bean.req.AbstractRequest;
import com.tutelary.common.utils.ParameterUtils;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = User.class)
public class UserAddRequest extends AbstractRequest {

    private String username;

    private String nickName;

    private String phoneNumber;

    private String password;

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
