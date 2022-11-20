package com.tutelary.common.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.common.bean.domain.BaseDomain;
import com.tutelary.common.bean.entity.BaseEntity;

import java.util.List;

public interface EntityDomainConverter<ENTITY extends BaseEntity, Domain extends BaseDomain> {

    Domain entityToDomain(ENTITY entity);

    List<Domain> entitiesToDomainList(List<ENTITY> entities);

    ENTITY domainToEntity(Domain dto);

    List<ENTITY> domainListToEntities(List<Domain> dtoList);

    PageResult<Domain> entityPageToDomainPage(Page<ENTITY> entityPage);
}
