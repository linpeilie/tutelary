package com.tutelary;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.Instance;
import com.tutelary.service.AppService;
import com.tutelary.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InstanceManager {

    private final Map<String, Instance> instanceMap = new ConcurrentHashMap<>();

    @Autowired
    private AppService appService;
    @Autowired
    private InstanceService instanceService;

    public Instance registerInstance(Instance instanceEntity) {
        String appName = instanceEntity.getAppName();

        createApp(appName);

        addInstance(instanceEntity);

        return instanceEntity;
    }

    public Instance getInstance(String instanceId) {
        return instanceMap.get(instanceId);
    }

    private void createApp(String appName) {
        if (appService.getAppByName(appName) != null) {
            return;
        }
        App app = new App();
        app.setAppName(appName);
        app.setRegisterDate(LocalDateTime.now());
        app.setInstanceNum(0);
        appService.addApp(app);
    }

    private App getApp(String appName) {
        return appService.getAppByName(appName);
    }

    private void addInstance(Instance instance) {
        appService.addInstance(instance.getAppName());
        instanceService.addInstance(instance);
        instanceMap.put(instance.getInstanceId(), instance);
    }

}
