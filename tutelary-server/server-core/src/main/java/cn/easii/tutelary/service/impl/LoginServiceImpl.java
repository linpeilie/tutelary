package cn.easii.tutelary.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ObjectUtil;
import cn.easii.tutelary.bean.domain.LoginUser;
import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.common.cache.CacheKeyTemplateEnum;
import cn.easii.tutelary.common.cache.CacheManager;
import cn.easii.tutelary.common.constants.CommonResponseCode;
import cn.easii.tutelary.common.enums.UserStateEnum;
import cn.easii.tutelary.common.exception.BusinessException;
import cn.easii.tutelary.common.utils.Asserts;
import cn.easii.tutelary.dao.UserDAO;
import cn.easii.tutelary.service.AuthService;
import cn.easii.tutelary.utils.AuthHelper;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final CacheManager cacheManager;

    @Value("${auth.login.fault.maxRetryCount:5}")
    private Integer loginMaxRetryCount;

    @Value("${auth.login.fault.lockTime:5}")
    private Integer loginFaultLockTime;

    private User loadUserByUsername(String username) {
        final User user = userDAO.getByUsername(username);
        Asserts.notNull(user, CommonResponseCode.AUTHENTICATION_FAILURE);
        Asserts.notEquals(user.getState(), UserStateEnum.DISABLED.getState(), CommonResponseCode.USER_STATE_DISABLED);
        return user;
    }

    private void checkLogin(String username, Supplier<Boolean> checkSupplier) {
        String loginFaultCntKey = CacheKeyTemplateEnum.LOGIN_FAULT_CNT.toCacheKey(username);
        final Integer faultNumber = ObjectUtil.defaultIfNull(cacheManager.get(loginFaultCntKey), 0);
        if (faultNumber >= loginMaxRetryCount) {
            throw new BusinessException(CommonResponseCode.USER_LOGIN_FAULT_EXCEED_MAX, loginMaxRetryCount, loginFaultLockTime);
        }
        if (!checkSupplier.get()) {
            // 错误次数 + 1
            cacheManager.set(loginFaultCntKey, faultNumber, loginFaultLockTime, TimeUnit.MINUTES);
            throw new BusinessException(CommonResponseCode.AUTHENTICATION_FAILURE);
        }
        cacheManager.delete(loginFaultCntKey);
    }

    private LoginUser buildLoginUser(User user) {
        final LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUsername());

        return loginUser;
    }

    @Override
    public String login(final String username, final String password) {
        final User user = loadUserByUsername(username);

        checkLogin(username, () -> BCrypt.checkpw(password, user.getPassword()));

        final LoginUser loginUser = buildLoginUser(user);

        AuthHelper.login(loginUser);

        return AuthHelper.getTokenValue();
    }

    @Override
    public void logout() {
        AuthHelper.logout();
    }
}
