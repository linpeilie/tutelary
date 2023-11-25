package cn.easii.tutelary.repository;

import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.bean.domain.query.UserQuery;
import cn.easii.tutelary.bean.entity.UserEntity;
import cn.easii.tutelary.common.repository.BaseRepository;
import cn.easii.tutelary.dao.UserDAO;

public interface UserRepository extends BaseRepository<UserQuery, User, UserEntity>, UserDAO {
}
