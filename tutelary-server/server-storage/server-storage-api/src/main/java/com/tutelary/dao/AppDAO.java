package com.tutelary.dao;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.dao.common.QueryDAO;

public interface AppDAO extends QueryDAO<AppQuery, App> {

    App getByName(String appName);

    boolean addInstance(String appName);

    boolean removeInstance(String appName);

}
