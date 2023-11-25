package cn.easii.tutelary.common.repository;

import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import cn.easii.tutelary.common.entity.BaseEntity;
import java.util.List;

public interface BaseRepository<QueryParam extends BaseQueryDomain, DTO extends BaseDomain, Entity extends BaseEntity> {

    boolean add(DTO dto);

    boolean edit(DTO dto);

    boolean addAll(List<DTO> dtoList);

    List<DTO> list(QueryParam queryParam);

    List<DTO> list(QueryParam q, final long pageIndex, final long pageSize);

    long count(QueryParam q);

}
