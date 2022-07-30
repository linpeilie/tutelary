package com.tutelary.repository;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.common.repository.BaseRepository;

public interface AppRepository extends BaseRepository<AppQuery, App, AppEntity> {

    App getByName(String appName);

    boolean addInstance(String appName);

}
