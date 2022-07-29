package com.tutelary.repository;

import com.tutelary.bean.dto.InstanceDTO;
import com.tutelary.bean.dto.InstanceQueryDTO;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceRepository extends BaseRepository<InstanceQueryDTO, InstanceDTO, InstanceEntity> {

    InstanceDTO getByInstanceId(String instanceId);

}
