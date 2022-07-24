package com.tutelary.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tutelary.bean.base.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutelary.bean.entity.AppEntity;
import com.tutelary.bean.entity.AppQueryEntity;
import com.tutelary.bean.vo.R;
import com.tutelary.service.AppService;

import cn.hutool.core.date.DateField;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/app")
public class AppController {

    @Autowired
    private AppService appService;

    @PostMapping(value = "pageQuery")
    public R<PageResult<AppEntity>> pageQuery(AppQueryEntity appQueryEntity) {
        log.info("appQueryEntity : {}", appQueryEntity);
        List<AppEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AppEntity appEntity = AppEntity.builder().appName(RandomUtil.randomString(8))
                .registerDate(RandomUtil.randomDate(new Date(), DateField.HOUR, -10000, 1000)).build();
            list.add(appEntity);
        }
        PageResult<AppEntity> pageResult = new PageResult<>();
        pageResult.setTotal(10L);
        pageResult.setRecords(list);
        return R.success(pageResult);
    }

}
