package com.tutelary.bean.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class LoginUser implements Serializable {

    private String userId;

    private String username;

    private String token;

    private Long loginTime;

    private Long expireTime;

    private String loginIp;

    private String loginLocation;

    private String browser;

    private String os;

}
