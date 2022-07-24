package com.tutelary.repository.impl;

import com.tutelary.bean.entity.AppEntity;
import com.tutelary.bean.entity.AppQueryEntity;
import com.tutelary.repository.AppRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AppRepositoryImpl implements AppRepository {

    private static final Map<String, AppEntity> MAP = new ConcurrentHashMap<>();

    @Override
    public boolean addApp(AppEntity appInfo) {
        return MAP.putIfAbsent(appInfo.getAppName(), appInfo) == null;
    }

    @Override
    public List<AppEntity> appList(AppQueryEntity appQueryEntity) {
        return new ArrayList<>(MAP.values());
    }

    @Override
    public AppEntity getByName(String appName) {
        return MAP.get(appName);
    }
}
