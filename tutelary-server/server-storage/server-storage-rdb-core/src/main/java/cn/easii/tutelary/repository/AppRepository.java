package cn.easii.tutelary.repository;

import cn.easii.tutelary.bean.domain.App;
import cn.easii.tutelary.bean.domain.query.AppQuery;
import cn.easii.tutelary.bean.entity.AppEntity;
import cn.easii.tutelary.common.repository.BaseRepository;
import cn.easii.tutelary.dao.AppDAO;

public interface AppRepository extends BaseRepository<AppQuery, App, AppEntity>, AppDAO {

    App getByName(String appName);

    boolean addInstance(String appName);

    boolean removeInstance(String appName);

}
