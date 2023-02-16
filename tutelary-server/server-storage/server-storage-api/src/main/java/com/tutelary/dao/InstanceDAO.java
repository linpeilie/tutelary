package com.tutelary.dao;

import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.dao.common.QueryDAO;
import java.util.List;

public interface InstanceDAO extends QueryDAO<InstanceQuery, Instance> {

    Instance getByInstanceId(String instanceId);

    boolean del(String instanceId);

    boolean validedInstance(String instanceId);

    boolean invalidedInstance(String instanceId);

    List<Instance> listEnabled();

}
