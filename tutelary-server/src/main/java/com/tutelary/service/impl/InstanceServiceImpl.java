package com.tutelary.service.impl;

import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.repository.InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutelary.bean.domain.Instance;
import com.tutelary.service.InstanceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    private InstanceRepository instanceRepository;

    @Override
    @Transactional
    public boolean addInstance(Instance instance) {
        return instanceRepository.add(instance);
    }

    @Override
    public Instance getInstanceByInstanceId(String instanceId) {
        return instanceRepository.getByInstanceId(instanceId);
    }

    @Override
    public PageResult<Instance> pageList(InstanceQuery instanceQuery, PageQueryRequest pageRequest) {
        return instanceRepository.pageList(instanceQuery, pageRequest);
    }

    @Override
    public List<Instance> list(InstanceQuery queryParam) {
        return instanceRepository.list(queryParam);
    }
}
