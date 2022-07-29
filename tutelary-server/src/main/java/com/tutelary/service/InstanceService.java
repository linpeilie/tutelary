package com.tutelary.service;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.InstanceDTO;
import com.tutelary.bean.dto.InstanceQueryDTO;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;

import java.util.List;

public interface InstanceService {

    boolean addInstance(InstanceDTO instanceEntity);

    InstanceDTO getInstanceByInstanceId(String instanceId);

    PageResult<InstanceDTO> pageList(InstanceQueryDTO instanceQuery, PageRequest pageRequest);

    List<InstanceDTO> list(InstanceQueryDTO queryParam);

}
