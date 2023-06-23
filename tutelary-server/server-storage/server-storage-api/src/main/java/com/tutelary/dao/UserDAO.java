package com.tutelary.dao;

import com.tutelary.bean.domain.User;
import com.tutelary.bean.domain.query.UserQuery;
import com.tutelary.dao.common.QueryDAO;

public interface UserDAO extends QueryDAO<UserQuery, User> {

    User getByUserName(String userName);

    User getByPhoneNumber(String phoneNumber);

    void edit(User user);

}
