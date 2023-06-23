package com.tutelary.service;

import com.tutelary.bean.domain.User;
import com.tutelary.bean.domain.query.UserQuery;
import java.util.List;

public interface UserService {

    void add(User user);

    void edit(User user);

    List<User> list(UserQuery userQuery, long pageIndex, long pageSize);

    List<User> list(UserQuery userQuery);

    long count(UserQuery userQuery);

}
