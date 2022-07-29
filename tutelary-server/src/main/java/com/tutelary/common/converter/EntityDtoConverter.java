package com.tutelary.common.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutelary.common.bean.vo.PageResult;

import java.util.List;

public interface EntityDtoConverter<ENTITY, DTO> {

    DTO entityToDto(ENTITY entity);

    List<DTO> entitiesToDtoList(List<ENTITY> entities);

    ENTITY dtoToEntity(DTO dto);

    List<ENTITY> dtoListToEntities(List<DTO> dtoList);

    PageResult<DTO> entityPageToDtoPage(Page<ENTITY> entityPage);
}
