package cn.easii.tutelary.service;

import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.bean.domain.query.UserQuery;
import java.util.List;

public interface UserService {

    void add(User user);

    void edit(User user);

    List<User> list(UserQuery userQuery, long pageIndex, long pageSize);

    List<User> list(UserQuery userQuery);

    long count(UserQuery userQuery);

    User loadUserByUsername(String username);

    User loadUserByUserId(String userId);

}
