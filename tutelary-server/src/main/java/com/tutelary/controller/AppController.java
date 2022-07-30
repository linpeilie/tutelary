package com.tutelary.controller;

import com.tutelary.bean.api.req.AppPageQueryRequest;
import com.tutelary.bean.api.req.AppQueryRequest;
import com.tutelary.bean.api.resp.AppInfoResponse;
import com.tutelary.bean.converter.AppConverter;
import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.common.bean.api.R;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping (value = "/api/app")
public class AppController {

    @Autowired
    private AppService appService;
    @Autowired
    private AppConverter appConverter;

    @PostMapping (value = "pageQuery")
    public R<PageResult<AppInfoResponse>> pageQuery(@RequestBody AppPageQueryRequest appPageQueryParam) {
        AppQuery queryParam = appConverter.pageQueryReqToDomain(appPageQueryParam);
        PageResult<App> pageResult = appService.pageListApp(queryParam, appPageQueryParam);
        return R.success(appConverter.domainPageResultToResponse(pageResult));
    }

    @PostMapping(value = "list")
    public R<List<AppInfoResponse>> list(@RequestBody AppQueryRequest appQueryRequest) {
        AppQuery appQuery = appConverter.queryRequestToDomain(appQueryRequest);
        List<App> list = appService.list(appQuery);
        return R.success(appConverter.domainListToResponse(list));
    }

}
