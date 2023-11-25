package cn.easii.tutelary.dao;

import cn.easii.tutelary.dao.common.QueryDAO;
import cn.easii.tutelary.bean.domain.InstanceHost;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;

public interface InstanceHostDAO extends QueryDAO<StatisticQuery, InstanceHost> {

    InstanceHost getByInstanceId(String instanceId);

    boolean update(InstanceHost instanceHost);

}
