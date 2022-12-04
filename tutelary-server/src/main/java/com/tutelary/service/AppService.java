package com.tutelary.service;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;

import java.util.List;

public interface AppService {

    App getAppByName(String appName);

    boolean addApp(App app);

    boolean addInstance(String appName);

    boolean removeInstance(String appName);

    PageResult<App> pageListApp(AppQuery queryParam, PageQueryRequest pageRequest);

    List<App> list(AppQuery appQuery);

}
