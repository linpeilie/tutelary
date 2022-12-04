package com.tutelary.service.impl;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.repository.AppRepository;
import com.tutelary.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author linpl
 */
@Component
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository appRepository;

    @Override
    public App getAppByName(String appName) {
        return appRepository.getByName(appName);
    }

    @Override
    public PageResult<App> pageListApp(AppQuery queryParam, PageQueryRequest pageRequest) {
        return appRepository.pageList(queryParam, pageRequest);
    }

    @Override
    public List<App> list(AppQuery appQuery) {
        return appRepository.list(appQuery);
    }

    @Override
    public boolean addApp(App app) {
        return appRepository.add(app);
    }

    @Override
    public boolean addInstance(String appName) {
        return appRepository.addInstance(appName);
    }

    @Override
    public boolean removeInstance(String appName) {
        return appRepository.removeInstance(appName);
    }
}
