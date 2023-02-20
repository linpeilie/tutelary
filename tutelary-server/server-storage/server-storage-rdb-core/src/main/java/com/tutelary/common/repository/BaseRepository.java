package com.tutelary.common.repository;

import com.tutelary.common.domain.BaseDomain;
import com.tutelary.common.domain.BaseQueryDomain;
import com.tutelary.common.entity.BaseEntity;
import java.util.List;

public interface BaseRepository<QueryParam extends BaseQueryDomain, DTO extends BaseDomain, Entity extends BaseEntity> {

    boolean add(DTO dto);

    boolean addAll(List<DTO> dtoList);

    List<DTO> list(QueryParam queryParam);

    List<DTO> list(QueryParam q, final long pageIndex, final long pageSize);

    long count(QueryParam q);

}
