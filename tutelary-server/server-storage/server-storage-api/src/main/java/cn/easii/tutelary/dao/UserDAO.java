package cn.easii.tutelary.dao;

import cn.easii.tutelary.dao.common.QueryDAO;
import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.bean.domain.query.UserQuery;

public interface UserDAO extends QueryDAO<UserQuery, User> {

    User getByUsername(String username);

    User getByUserId(String userId);

    User getByPhoneNumber(String phoneNumber);

    boolean edit(User user);

}
