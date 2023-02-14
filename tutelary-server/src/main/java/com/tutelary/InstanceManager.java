package com.tutelary;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.Instance;
import com.tutelary.service.AppService;
import com.tutelary.service.InstanceService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstanceManager {

    private static final Map<String, Instance> INSTANCE_MAP = new ConcurrentHashMap<>();

    @Autowired
    private AppService appService;
    @Autowired
    private InstanceService instanceService;

    @PostConstruct
    private void reset() {
        List<Instance> instances = instanceService.listEnabled();
        instances.forEach(this::removeInstance);
    }

    public Instance registerInstance(Instance instanceEntity) {

        String appName = instanceEntity.getAppName();

        createApp(appName);

        addInstance(instanceEntity);

        return instanceEntity;
    }

    private void removeInstance(String instanceId) {
        Instance instance = instanceService.getInstanceByInstanceId(instanceId);
        removeInstance(instance);
    }

    private void removeInstance(Instance instance) {
        instanceService.invalidInstance(instance.getInstanceId());
        appService.removeInstance(instance.getAppName());
    }

    public Optional<Instance> getInstance(String instanceId) {
        return Optional.ofNullable(INSTANCE_MAP.get(instanceId));
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
        INSTANCE_MAP.put(instance.getInstanceId(), instance);
    }

}
