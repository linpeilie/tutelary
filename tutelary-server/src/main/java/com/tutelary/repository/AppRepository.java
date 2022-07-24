package com.tutelary.repository;

import com.tutelary.bean.entity.AppEntity;
import com.tutelary.bean.entity.AppQueryEntity;

import java.util.List;

public interface AppRepository {

    boolean addApp(AppEntity appInfo);

    List<AppEntity> appList(AppQueryEntity appQueryEntity);

    AppEntity getByName(String appName);

}
