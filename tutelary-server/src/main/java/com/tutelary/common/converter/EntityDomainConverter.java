package com.tutelary.common.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutelary.common.bean.api.resp.PageResult;

import java.util.List;

public interface EntityDomainConverter<ENTITY, Domain> {

    Domain entityToDomain(ENTITY entity);

    List<Domain> entitiesToDomainList(List<ENTITY> entities);

    ENTITY domainToEntity(Domain dto);

    List<ENTITY> domainListToEntities(List<Domain> dtoList);

    PageResult<Domain> entityPageToDomainPage(Page<ENTITY> entityPage);
}
