package com.tutelary.controller;

import com.tutelary.bean.converter.InstanceConverter;
import com.tutelary.bean.dto.InstanceDTO;
import com.tutelary.bean.dto.InstanceQueryDTO;
import com.tutelary.bean.vo.AppVO;
import com.tutelary.bean.vo.InstancePageQueryVO;
import com.tutelary.bean.vo.InstanceVO;
import com.tutelary.common.bean.vo.PageResult;
import com.tutelary.common.bean.vo.R;
import com.tutelary.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping (value = "/api/instance")
public class InstanceController {

    @Autowired
    private InstanceService instanceService;
    @Autowired
    private InstanceConverter instanceConverter;

    @PostMapping (value = "pageQuery")
    public R<PageResult<InstanceVO>> pageQuery(@RequestBody InstancePageQueryVO instancePageQueryParam) {
        InstanceQueryDTO queryParam = instanceConverter.pageQueryVoToDto(instancePageQueryParam);
        PageResult<InstanceDTO> pageResult = instanceService.pageList(queryParam, instancePageQueryParam);
        return R.success(instanceConverter.dtoPageToVoPage(pageResult));
    }

    public R<List<InstanceVO>> list(@RequestBody )

}
