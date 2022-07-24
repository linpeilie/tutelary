package com.tutelary.service;

import com.tutelary.bean.entity.AppEntity;

public interface AppService {

    AppEntity getAppByName(String appName);

    boolean addApp(AppEntity app);

}
