package com.tutelary.service.impl;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.dao.AppDAO;
import com.tutelary.service.AppService;
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
    public PageResult<App> pageListApp(AppQuery queryParam, PageQueryRequest pageRequest) {
        // TODO
        return null;
    }

    @Override
    public List<App> list(AppQuery appQuery) {
        return appDAO.list(appQuery);
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
