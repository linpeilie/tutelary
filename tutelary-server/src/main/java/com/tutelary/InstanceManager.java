package com.tutelary;

import com.tutelary.bean.entity.AppEntity;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.service.AppService;
import com.tutelary.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InstanceManager {

    private static final Map<String, InstanceEntity> INSTANCE_MAP = new ConcurrentHashMap<>();

    @Autowired
    private AppService appService;
    @Autowired
    private InstanceService instanceService;

    public InstanceEntity registerInstance(InstanceEntity instanceEntity) {
        String appName = instanceEntity.getAppName();

        createApp(appName);

        boolean addSuccess = addInstance(instanceEntity);
        if (addSuccess) {
            INSTANCE_MAP.putIfAbsent(instanceEntity.getInstanceId(), instanceEntity);
        }

        return instanceEntity;
    }

    public InstanceEntity getInstance(String instanceId) {
        return INSTANCE_MAP.get(instanceId);
    }

    private void createApp(String appName) {
        if (appService.getAppByName(appName) == null) {
            return;
        }
        AppEntity appEntity = new AppEntity();
        appEntity.setAppName(appName);
        appEntity.setRegisterDate(new Date());
        appService.addApp(appEntity);
    }

    private AppEntity getApp(String appName) {
        return appService.getAppByName(appName);
    }

    private boolean addInstance(InstanceEntity instanceEntity) {
        return instanceService.addInstance(instanceEntity);
    }

}
