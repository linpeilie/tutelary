package com.tutelary.service.impl;

import com.tutelary.constance.PersistentMannerConstants;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.service.AppService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linpl
 */
@Component
@ConditionalOnProperty(name = "tutelary.server.persistent", havingValue = PersistentMannerConstants.MEMORY)
public class MemoryAppServiceImpl implements AppService {

    private final Map<String, AppEntity> APP_MAP;

    public MemoryAppServiceImpl() {
        APP_MAP = new ConcurrentHashMap<>();
    }

    @Override
    public AppEntity getAppByName(String appName) {
        return APP_MAP.get(appName);
    }

    @Override
    public boolean addApp(AppEntity app) {
        return APP_MAP.putIfAbsent(app.getAppName(), app) == null;
    }
}
