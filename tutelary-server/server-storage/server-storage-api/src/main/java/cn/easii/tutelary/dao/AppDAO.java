package cn.easii.tutelary.dao;

import cn.easii.tutelary.dao.common.QueryDAO;
import cn.easii.tutelary.bean.domain.App;
import cn.easii.tutelary.bean.domain.query.AppQuery;

public interface AppDAO extends QueryDAO<AppQuery, App> {

    App getByName(String appName);

}
