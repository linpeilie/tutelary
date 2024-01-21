package cn.easii.tutelary.service;

import cn.easii.tutelary.bean.domain.App;
import cn.easii.tutelary.bean.domain.query.AppQuery;
import java.util.List;

public interface AppService {

    App getAppByName(String appName);

    boolean addApp(App app);

    boolean addInstance(String appName, String instanceId);

    boolean removeInstance(String appName, String instanceId);

    List<App> list(AppQuery appQuery);

    List<App> list(AppQuery appQuery, long pageIndex, long pageSize);

    long count(AppQuery appQuery);

    List<String> listInstanceIdsByAppName(String appName);

}
