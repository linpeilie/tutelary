package com.tutelary.common.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tutelary.common.bean.dto.BaseDto;
import com.tutelary.common.bean.dto.BaseQueryDto;
import com.tutelary.common.bean.entity.BaseEntity;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;

import java.util.List;

public interface BaseRepository<QueryParam extends BaseQueryDto, DTO extends BaseDto, Entity extends BaseEntity> {

    boolean add(DTO dto);

    List<DTO> list(QueryParam queryParam);

    PageResult<DTO> pageList(QueryParam queryParam, PageRequest pageRequest);

}
