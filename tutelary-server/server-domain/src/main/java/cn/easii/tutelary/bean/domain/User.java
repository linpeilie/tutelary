package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.common.enums.UserStateEnum;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class User extends BaseDomain {

    private String userId;

    private String username;

    private String nickName;

    private String phoneNumber;

    private String password;

    /**
     * {@link UserStateEnum}
     */
    private String state;

    private String loginIp;

    private LocalDateTime loginDate;

    private String remark;

}
