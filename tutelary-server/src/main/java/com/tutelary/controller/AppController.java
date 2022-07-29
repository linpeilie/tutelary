package com.tutelary.controller;

import com.tutelary.bean.converter.AppConverter;
import com.tutelary.bean.vo.AppPageQueryVO;
import com.tutelary.bean.vo.AppVO;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.AppQueryDTO;
import com.tutelary.common.bean.vo.R;
import com.tutelary.service.AppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping (value = "/api/app")
public class AppController {

    @Autowired
    private AppService appService;
    @Autowired
    private AppConverter appConverter;

    @PostMapping (value = "pageQuery")
    public R<PageResult<AppVO>> pageQuery(@RequestBody AppPageQueryVO appPageQueryParam) {
        AppQueryDTO queryParam = appConverter.pageQueryVoToDto(appPageQueryParam);
        PageResult<AppDTO> pageResult = appService.pageListApp(queryParam, appPageQueryParam);
        return R.success(appConverter.dtoPageToVoPage(pageResult));
    }

}
