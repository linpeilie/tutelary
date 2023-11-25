package cn.easii.tutelary.service.impl;

import cn.easii.tutelary.bean.domain.App;
import cn.easii.tutelary.bean.domain.query.AppQuery;
import cn.easii.tutelary.dao.AppDAO;
import cn.easii.tutelary.service.AppService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author linpl
 */
@Component
public class AppServiceImpl implements AppService {

    private AppDAO appDAO;

    @Override
    public App getAppByName(String appName) {
        return appDAO.getByName(appName);
    }

    @Override
    public List<App> list(AppQuery appQuery) {
        return appDAO.list(appQuery);
    }

    @Override
    public List<App> list(final AppQuery appQuery, final long pageIndex, final long pageSize) {
        return appDAO.list(appQuery, pageIndex, pageSize);
    }

    @Override
    public long count(final AppQuery appQuery) {
        return appDAO.count(appQuery);
    }

    @Override
    public boolean addApp(App app) {
        return appDAO.add(app);
    }

    @Override
    public boolean addInstance(String appName) {
        return appDAO.addInstance(appName);
    }

    @Override
    public boolean removeInstance(String appName) {
        return appDAO.removeInstance(appName);
    }

    @Autowired
    public void setAppDAO(final AppDAO appDAO) {
        this.appDAO = appDAO;
    }
}
