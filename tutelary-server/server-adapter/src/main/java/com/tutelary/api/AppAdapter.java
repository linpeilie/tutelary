package com.tutelary.api;

import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.bean.req.AppPageQueryRequest;
import com.tutelary.bean.req.AppQueryRequest;
import com.tutelary.bean.resp.AppInfoResponse;
import com.tutelary.common.bean.R;
import com.tutelary.common.bean.resp.PageResult;
import com.tutelary.service.AppService;
import io.github.zhaord.mapstruct.plus.IObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/app")
public class AppAdapter {

    private AppService appService;
    private IObjectMapper objectMapper;

    @PostMapping(value = "pageQuery")
    public R<PageResult<AppInfoResponse>> pageQuery(@RequestBody AppPageQueryRequest appPageQueryParam) {
        AppQuery queryParam = objectMapper.map(appPageQueryParam, AppQuery.class);
        final long count = appService.count(queryParam);
        if (count > 0) {
            final List<App> list =
                appService.list(queryParam, appPageQueryParam.getPageIndex(), appPageQueryParam.getPageSize());
            return R.success(PageResult.of(count, objectMapper.mapList(list, AppInfoResponse.class)));
        }
        return R.success(PageResult.empty());
    }

    @PostMapping(value = "list")
    public R<List<AppInfoResponse>> list(@RequestBody AppQueryRequest appQueryRequest) {
        AppQuery appQuery = objectMapper.map(appQueryRequest, AppQuery.class);
        List<App> list = appService.list(appQuery);
        return R.success(objectMapper.mapList(list, AppInfoResponse.class));
    }

    @Autowired
    public void setAppService(final AppService appService) {
        this.appService = appService;
    }

    @Autowired
    public void setObjectMapper(final IObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
