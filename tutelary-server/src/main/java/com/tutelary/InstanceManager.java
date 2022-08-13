package com.tutelary;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Objects;
import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.Instance;
import com.tutelary.service.AppService;
import com.tutelary.service.InstanceService;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InstanceManager {

    private static final Map<String, Instance> INSTANCE_MAP = new ConcurrentHashMap<>();

    private static final AttributeKey<String> INSTANCE_ID_KEY = AttributeKey.valueOf("instanceId");

    @Autowired
    private AppService appService;
    @Autowired
    private InstanceService instanceService;

    public Instance registerInstance(Instance instanceEntity) {

        String appName = instanceEntity.getAppName();

        createApp(appName);

        bindChannel(instanceEntity.getInstanceId(), instanceEntity.getChannel());

        addInstance(instanceEntity);

        return instanceEntity;
    }

    public void removeChannel(Channel channel) {
        String instanceId = channel.attr(INSTANCE_ID_KEY).get();
        if (StrUtil.isNotEmpty(instanceId)) {
            instanceService.removeInstance(instanceId);
            INSTANCE_MAP.remove(instanceId);
        }
    }

    public boolean isBind(Channel channel) {
        String instanceId = channel.attr(INSTANCE_ID_KEY).get();
        return StrUtil.isNotEmpty(instanceId) && INSTANCE_MAP.containsKey(instanceId);
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

    private void bindChannel(String instanceId, Channel channel) {
        channel.attr(INSTANCE_ID_KEY).set(instanceId);
    }

    private void addInstance(Instance instance) {
        appService.addInstance(instance.getAppName());
        instanceService.addInstance(instance);
        INSTANCE_MAP.put(instance.getInstanceId(), instance);
    }

}
