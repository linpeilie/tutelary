package com.tutelary;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.InstanceDTO;
import com.tutelary.service.AppService;
import com.tutelary.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InstanceManager {

    @Autowired
    private AppService appService;
    @Autowired
    private InstanceService instanceService;

    public InstanceDTO registerInstance(InstanceDTO instanceEntity) {
        String appName = instanceEntity.getAppName();

        createApp(appName);

        addInstance(instanceEntity);

        return instanceEntity;
    }

    public InstanceDTO getInstance(String instanceId) {
        return instanceService.getInstanceByInstanceId(instanceId);
    }

    private void createApp(String appName) {
        if (appService.getAppByName(appName) == null) {
            return;
        }
        AppDTO appDTO = new AppDTO();
        appDTO.setAppName(appName);
        appDTO.setRegisterDate(LocalDateTime.now());
        appDTO.setInstanceNum(0);
        appService.addApp(appDTO);
    }

    private AppDTO getApp(String appName) {
        return appService.getAppByName(appName);
    }

    private void addInstance(InstanceDTO instance) {
        appService.addInstance(instance.getAppName());
        instanceService.addInstance(instance);
    }

}
