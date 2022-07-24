package com.tutelary.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashMultimap;
import com.tutelary.constance.PersistentMannerConstants;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.service.InstanceService;

@Component
@ConditionalOnProperty(name = "tutelary.server.persistent", havingValue = PersistentMannerConstants.MEMORY)
public class MemoryInstanceServiceImpl implements InstanceService {

    private final HashMultimap<String, InstanceEntity> INSTANCE_MAP;

    public MemoryInstanceServiceImpl() {
        INSTANCE_MAP = HashMultimap.create();
    }

    @Override
    public boolean addInstance(InstanceEntity instanceEntity) {
        return INSTANCE_MAP.put(instanceEntity.getAppName(), instanceEntity);
    }
}
