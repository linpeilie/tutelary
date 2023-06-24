package com.tutelary.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.stp.StpUtil;
import com.tutelary.bean.domain.LoginUser;

public class LoginHelper {

    public static final String LOGIN_USER_KEY = "loginUser";
    public static final String USER_KEY = "userId";

    public static void login(LoginUser loginUser) {
        final SaStorage storage = SaHolder.getStorage();
        storage.set(LOGIN_USER_KEY, loginUser);
        storage.set(USER_KEY, loginUser.getUserId());

        StpUtil.login(loginUser.getUserId());

        StpUtil.getTokenSession().set(LOGIN_USER_KEY, loginUser);
    }

}
