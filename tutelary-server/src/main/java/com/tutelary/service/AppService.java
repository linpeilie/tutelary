package com.tutelary.service;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.AppQueryDTO;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;

public interface AppService {

    AppDTO getAppByName(String appName);

    boolean addApp(AppDTO app);

    boolean addInstance(String appName);

    PageResult<AppDTO> pageListApp(AppQueryDTO queryParam, PageRequest pageRequest);

}
