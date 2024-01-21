package cn.easii.tutelary;

import cn.easii.tutelary.service.AppService;
import cn.easii.tutelary.service.InstanceService;
import cn.easii.tutelary.bean.domain.App;
import cn.easii.tutelary.bean.domain.Instance;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstanceManager {

    private static final Map<String, Instance> INSTANCE_MAP = new ConcurrentHashMap<>();

    private final AppService appService;
    private final InstanceService instanceService;

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
        appService.removeInstance(instance.getAppName(), instance.getInstanceId());
        INSTANCE_MAP.remove(instance.getInstanceId());
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
        appService.addApp(app);
    }

    private App getApp(String appName) {
        return appService.getAppByName(appName);
    }

    private void addInstance(Instance instance) {
        appService.addInstance(instance.getAppName(), instance.getInstanceId());
        instanceService.addInstance(instance);
        INSTANCE_MAP.put(instance.getInstanceId(), instance);
    }

}
