package com.tutelary.bean.resp;

import com.tutelary.bean.domain.User;
import com.tutelary.common.bean.resp.AbstractResponse;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(name = "UserInfoResponse", description = "用户信息返回参数")
@AutoMapper(target = User.class, convertGenerate = false)
public class UserInfoResponse extends AbstractResponse {


    @Schema(name = "userId", description = "用户ID")
    private String userId;

    @Schema(name = "username", description = "用户账号")
    private String username;

    @Schema(name = "nickName", description = "用户昵称")
    private String nickName;

    @Schema(name = "phoneNumber", description = "用户手机号")
    private String phoneNumber;

    @Schema(name = "state", description = "用户状态「00-正常；10-停用」")
    private String state;

    @Schema(name = "loginIp", description = "上次登录IP")
    private String loginIp;

    @Schema(name = "loginDate", description = "上次登录时间")
    private LocalDateTime loginDate;

    @Schema(name = "remark", description = "备注")
    private String remark;

    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

}
