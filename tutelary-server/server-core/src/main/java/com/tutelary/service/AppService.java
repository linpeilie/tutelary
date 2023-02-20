package com.tutelary.service;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import java.util.List;

public interface AppService {

    App getAppByName(String appName);

    boolean addApp(App app);

    boolean addInstance(String appName);

    boolean removeInstance(String appName);

    List<App> list(AppQuery appQuery);

    List<App> list(AppQuery appQuery, long pageIndex, long pageSize);

    long count(AppQuery appQuery);

}
