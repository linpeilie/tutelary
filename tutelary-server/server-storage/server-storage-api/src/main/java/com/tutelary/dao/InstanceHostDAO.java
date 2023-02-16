package com.tutelary.dao;

import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.dao.common.Dao;
import com.tutelary.dao.common.QueryDAO;

public interface InstanceHostDAO extends QueryDAO<StatisticQuery, InstanceHost> {

    InstanceHost getByInstanceId(String instanceId);

    boolean update(InstanceHost instanceHost);

}
