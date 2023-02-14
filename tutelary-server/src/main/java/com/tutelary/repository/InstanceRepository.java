package com.tutelary.repository;

import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.common.repository.BaseRepository;
import java.util.List;

public interface InstanceRepository extends BaseRepository<InstanceQuery, Instance, InstanceEntity> {

    Instance getByInstanceId(String instanceId);

    boolean del(String instanceId);

    boolean validedInstance(String instanceId);

    boolean invalidedInstance(String instanceId);

    List<Instance> listEnabled();

}
