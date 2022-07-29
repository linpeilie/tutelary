package com.tutelary.service.impl;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.AppQueryDTO;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;
import com.tutelary.repository.AppRepository;
import com.tutelary.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linpl
 */
@Component
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository appRepository;

    @Override
    public AppDTO getAppByName(String appName) {
        return appRepository.getByName(appName);
    }

    @Override
    public PageResult<AppDTO> pageListApp(AppQueryDTO queryParam, PageRequest pageRequest) {
        return appRepository.pageList(queryParam, pageRequest);
    }

    @Override
    public boolean addApp(AppDTO app) {
        return appRepository.add(app);
    }

    @Override
    public boolean addInstance(String appName) {
        return appRepository.addInstance(appName);
    }
}
