package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseDomain;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class User extends BaseDomain {

    private String userId;

    private String userName;

    private String nickName;

    private String phoneNumber;

    private String password;

    /**
     * {@link com.tutelary.common.enums.UserStateEnum}
     */
    private String state;

    private String loginIp;

    private LocalDateTime loginDate;

    private String remark;

}
