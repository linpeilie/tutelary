package com.tutelary.repository;

import com.tutelary.bean.domain.User;
import com.tutelary.bean.domain.query.UserQuery;
import com.tutelary.bean.entity.UserEntity;
import com.tutelary.common.repository.BaseRepository;
import com.tutelary.dao.UserDAO;

public interface UserRepository extends BaseRepository<UserQuery, User, UserEntity>, UserDAO {
}
