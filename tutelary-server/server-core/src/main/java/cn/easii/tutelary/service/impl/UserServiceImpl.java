package cn.easii.tutelary.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.bean.domain.query.UserQuery;
import cn.easii.tutelary.common.constants.SystemResponseCode;
import cn.easii.tutelary.common.enums.UserStateEnum;
import cn.easii.tutelary.common.utils.Asserts;
import cn.easii.tutelary.dao.UserDAO;
import cn.easii.tutelary.service.UserService;
import cn.easii.tutelary.utils.IdGeneratorHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public void add(final User user) {
        // 校验重复
        checkUnique(user);
        // 初始化用户信息
        userAddInfoInitial(user);
        // 密码加密
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        // 保存用户信息
        userDAO.add(user);
    }

    private void checkUnique(User user) {
        Asserts.isNull(userDAO.getByUsername(user.getUsername()), "用户账号已存在");
        Asserts.isNull(userDAO.getByPhoneNumber(user.getPhoneNumber()), "用户手机号已存在");
    }

    private void userAddInfoInitial(User user) {
        user.setState(UserStateEnum.AVAILABLE.getState());
        user.setUserId(IdGeneratorHelper.getId());
    }

    @Override
    public void edit(User user) {
        Asserts.notNull(userDAO.getByUserId(user.getUserId()), SystemResponseCode.USER_NOT_EXISTED, user.getUserId());
        Asserts.isNull(userDAO.getByPhoneNumber(user.getPhoneNumber()), "用户手机号已存在");
        userDAO.edit(user);
    }

    @Override
    public List<User> list(UserQuery userQuery, long pageIndex, long pageSize) {
        return userDAO.list(userQuery, pageIndex, pageSize);
    }

    @Override
    public List<User> list(UserQuery userQuery) {
        return userDAO.list(userQuery);
    }

    @Override
    public long count(UserQuery userQuery) {
        return userDAO.count(userQuery);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userDAO.getByUsername(username);
    }

    @Override
    public User loadUserByUserId(String userId) {
        return userDAO.getByUserId(userId);
    }
}
