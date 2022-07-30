package com.tutelary.service;

import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.bean.api.resp.PageResult;

import java.util.List;

public interface InstanceService {

    boolean addInstance(Instance instanceEntity);

    Instance getInstanceByInstanceId(String instanceId);

    PageResult<Instance> pageList(InstanceQuery instanceQuery, PageQueryRequest pageRequest);

    List<Instance> list(InstanceQuery queryParam);

}
