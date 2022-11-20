package com.tutelary.common.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutelary.common.bean.api.resp.PageResult;
import com.tutelary.common.bean.domain.BaseDomain;
import com.tutelary.common.bean.domain.BaseQueryDomain;
import com.tutelary.common.bean.entity.BaseEntity;
import com.tutelary.common.bean.api.req.PageQueryRequest;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.common.helper.MybatisPlusQueryHelper;

import java.util.List;

public abstract class AbstractRepository<Q extends BaseQueryDomain, D extends BaseDomain, E extends BaseEntity, M extends BaseMapper<E>>
    extends ServiceImpl<M, E> implements BaseRepository<Q, D, E> {

    protected EntityDomainConverter<E, D> converter;

    public AbstractRepository(EntityDomainConverter<E, D> converter) {
        this.converter = converter;
    }

    @Override
    public boolean add(D dto) {
        return super.save(converter.domainToEntity(dto));
    }

    @Override
    public boolean addAll(List<D> ds) {
        return super.saveBatch(converter.domainListToEntities(ds));
    }

    @Override
    public List<D> list(Q q) {
        LambdaQueryWrapper<E> queryWrapper = MybatisPlusQueryHelper.buildQueryWrapper(q);
        return converter.entitiesToDomainList(super.list(queryWrapper));
    }

    @Override
    public PageResult<D> pageList(Q q, PageQueryRequest pageRequest) {
        LambdaQueryWrapper<E> queryWrapper = MybatisPlusQueryHelper.buildQueryWrapper(q);
        Page<E> page = Page.of(pageRequest.getPageNo(), pageRequest.getPageSize());
        return converter.entityPageToDomainPage(super.page(page, queryWrapper));
    }
}
