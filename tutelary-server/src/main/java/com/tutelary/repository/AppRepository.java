package com.tutelary.repository;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.AppQueryDTO;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;
import com.tutelary.common.repository.BaseRepository;

import java.util.List;

public interface AppRepository extends BaseRepository<AppQueryDTO, AppDTO, AppEntity> {

    AppDTO getByName(String appName);

    boolean addInstance(String appName);

}
