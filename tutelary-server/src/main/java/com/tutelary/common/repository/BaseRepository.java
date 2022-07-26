package com.tutelary.common.repository;

import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.common.bean.domain.BaseDomain;
import com.tutelary.common.bean.domain.BaseQueryDomain;
import com.tutelary.common.bean.entity.BaseEntity;
import com.tutelary.common.bean.api.req.PageQueryRequest;

import java.util.List;

public interface BaseRepository<QueryParam extends BaseQueryDomain, DTO extends BaseDomain, Entity extends BaseEntity> {

    boolean add(DTO dto);

    List<DTO> list(QueryParam queryParam);

    PageResult<DTO> pageList(QueryParam queryParam, PageQueryRequest pageRequest);

}
