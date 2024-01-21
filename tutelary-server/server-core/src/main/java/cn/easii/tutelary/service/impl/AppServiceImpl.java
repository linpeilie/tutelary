package cn.easii.tutelary.service.impl;

import cn.easii.tutelary.bean.domain.App;
import cn.easii.tutelary.bean.domain.query.AppQuery;
import cn.easii.tutelary.dao.AppDAO;
import cn.easii.tutelary.service.AppService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author linpl
 */
@Component
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    /**
     * app 对应实例集合
     */
    private static final Multimap<String, String> APP_INSTANCE_MULTIMAP = HashMultimap.create();

    private final AppDAO appDAO;

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
        List<App> apps = appDAO.list(appQuery, pageIndex, pageSize);
        // instance count
        apps.forEach(app -> {
            app.setInstanceNum(APP_INSTANCE_MULTIMAP.get(app.getAppName()).size());
        });
        return apps;
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
    public boolean addInstance(String appName, String instanceId) {
        return APP_INSTANCE_MULTIMAP.put(appName, instanceId);
    }

    @Override
    public boolean removeInstance(String appName, String instanceId) {
        return APP_INSTANCE_MULTIMAP.remove(appName, instanceId);
    }

    @Override
    public List<String> listInstanceIdsByAppName(String appName) {
        return new ArrayList<>(APP_INSTANCE_MULTIMAP.get(appName));
    }
}
