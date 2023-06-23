package com.tutelary.bean.req;

import com.tutelary.bean.domain.User;
import com.tutelary.common.bean.req.AbstractRequest;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(name = "UserEditRequest", description = "用户信息编辑入参")
@AutoMapper(target = User.class, reverseConvertGenerate = false)
public class UserEditRequest extends AbstractRequest {

    @Schema(name = "userId", description = "用户ID")
    private String userId;

    @Schema(name = "userName", description = "用户账号")
    private String userName;

    @Schema(name = "nickName", description = "用户昵称")
    private String nickName;

    @Schema(name = "phoneNumber", description = "用户手机号")
    private String phoneNumber;

    @Schema(name = "password", description = "密码")
    private String password;

    @Schema(name = "state", description = "用户状态「00-正常；10-停用」")
    private String state;

    @Schema(name = "loginIp", description = "上次登录IP")
    private String loginIp;

    @Schema(name = "loginDate", description = "上次登录时间")
    private LocalDateTime loginDate;

    @Schema(name = "remark", description = "备注")
    private String remark;

}