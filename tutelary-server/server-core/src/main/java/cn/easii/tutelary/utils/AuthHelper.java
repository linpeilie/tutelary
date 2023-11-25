package cn.easii.tutelary.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.easii.tutelary.common.constants.Constants;
import cn.easii.tutelary.bean.domain.LoginUser;

public class AuthHelper {

    /**
     * 返回 token 名称，此名称在以下地方体现：Cookie 保存 token 时的名称、提交 token 时参数的名称、存储 token 时的 key 前缀
     */
    public static String getTokenName() {
        return StpUtil.getTokenName();
    }

    /**
     * 获取当前请求的 token 值
     * @return  当前请求的 token 值
     */
    public static String getTokenValue() {
        return StpUtil.getTokenValue();
    }

    /**
     * 判断 token 是否登录
     *
     * @param token token值
     * @return 已经登录则返回true，否则返回false
     */
    public static boolean isLogin(String token) {
        return StpUtil.getLoginIdByToken(token) != null;
    }

    public static boolean isLoginByUserId(String userId) {
        return StpUtil.isLogin(userId);
    }

    /**
     * 获取当前登录用户的ID
     */
    public static String getUserId() {
        final Object loginId = StpUtil.getLoginIdDefaultNull();
        return loginId == null ? null : String.valueOf(loginId);
    }

    public static LoginUser loginUser() {
        final String tokenValue = getTokenValue();
        if (tokenValue == null) {
            return null;
        }
        return getLoginUser(tokenValue);
    }

    public static LoginUser getLoginUser(String token) {
        final SaSession session = StpUtil.getTokenSessionByToken(token);
        return session.getModel(Constants.Authentication.LOGIN_USER_KEY, LoginUser.class);
    }

    public static String getUserIdByToken(String token) {
        final Object userId = StpUtil.getLoginIdByToken(token);
        return userId == null ? null : String.valueOf(userId);
    }

    public static String getTokenByUserId(String userId) {
        return StpUtil.getTokenValueByLoginId(userId);
    }

    public static void login(LoginUser loginUser) {
        StpUtil.login(loginUser.getUserId());
        StpUtil.getTokenSession().set(Constants.Authentication.LOGIN_USER_KEY, loginUser);
    }

    public static void logout() {
        StpUtil.logout();
    }

}
