package com.tutelary.service.impl;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.InstanceQueryDTO;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;
import com.tutelary.repository.InstanceRepository;
import com.tutelary.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.HashMultimap;
import com.tutelary.bean.dto.InstanceDTO;
import com.tutelary.service.InstanceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    private InstanceRepository instanceRepository;

    @Override
    @Transactional
    public boolean addInstance(InstanceDTO instance) {
        return instanceRepository.add(instance);
    }

    @Override
    public InstanceDTO getInstanceByInstanceId(String instanceId) {
        return instanceRepository.getByInstanceId(instanceId);
    }

    @Override
    public PageResult<InstanceDTO> pageList(InstanceQueryDTO instanceQuery, PageRequest pageRequest) {
        return instanceRepository.pageList(instanceQuery, pageRequest);
    }

    @Override
    public List<InstanceDTO> list(InstanceQueryDTO queryParam) {
        return instanceRepository.list(queryParam);
    }
}
