package cn.easii.tutelary.repository;

import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import cn.easii.tutelary.bean.entity.InstanceEntity;
import cn.easii.tutelary.common.repository.BaseRepository;
import cn.easii.tutelary.dao.InstanceDAO;
import java.util.List;

public interface InstanceRepository extends BaseRepository<InstanceQuery, Instance, InstanceEntity>, InstanceDAO {

    Instance getByInstanceId(String instanceId);

    boolean del(String instanceId);

    boolean validedInstance(String instanceId);

    boolean invalidedInstance(String instanceId);

    List<Instance> listEnabled();

}
