package com.tutelary.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.stp.StpUtil;
import com.tutelary.bean.domain.LoginUser;
import com.tutelary.common.constants.Constants;

public class LoginHelper {

    public static void login(LoginUser loginUser) {
        StpUtil.login(loginUser.getUserId());
        StpUtil.getTokenSession().set(Constants.Authentication.LOGIN_USER_KEY, loginUser);
    }

}
