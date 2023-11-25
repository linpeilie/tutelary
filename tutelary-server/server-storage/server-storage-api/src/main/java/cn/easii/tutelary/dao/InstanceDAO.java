package cn.easii.tutelary.dao;

import cn.easii.tutelary.dao.common.QueryDAO;
import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import java.util.List;

public interface InstanceDAO extends QueryDAO<InstanceQuery, Instance> {

    Instance getByInstanceId(String instanceId);

    boolean del(String instanceId);

    boolean validedInstance(String instanceId);

    boolean invalidedInstance(String instanceId);

    List<Instance> listEnabled();

}
