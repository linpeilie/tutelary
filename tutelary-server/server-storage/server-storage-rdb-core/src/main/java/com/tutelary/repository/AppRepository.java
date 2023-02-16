package com.tutelary.repository;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.common.repository.BaseRepository;
import com.tutelary.dao.AppDAO;

public interface AppRepository extends BaseRepository<AppQuery, App, AppEntity>, AppDAO {

    App getByName(String appName);

    boolean addInstance(String appName);

    boolean removeInstance(String appName);

}
