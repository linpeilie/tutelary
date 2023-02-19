package com.tutelary.common.repository;

import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutelary.common.domain.BaseDomain;
import com.tutelary.common.domain.BaseQueryDomain;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.common.helper.MybatisPlusQueryHelper;
import com.tutelary.common.utils.ClassUtil;
import io.github.zhaord.mapstruct.plus.IObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository<Q extends BaseQueryDomain, D extends BaseDomain, E extends BaseEntity, M extends BaseMapper<E>>
    extends ServiceImpl<M, E> implements BaseRepository<Q, D, E> {

    @Autowired
    protected IObjectMapper converter;

    public AbstractRepository() {
    }

    private Class<?> getDomainClass() {
        return TypeUtil.getClass(TypeUtil.getTypeArgument(getClass(), 1));
    }

    protected D entityToDomain(E entity) {
        return entity == null ? null : converter.map(entity, getDomainClass());
    }

    protected List<D> entitiesToDomainList(List<E> entities) {
        return entities.stream().map(this::entityToDomain).collect(Collectors.toList());
    }

    protected E domainToEntity(D domain) {
        return domain == null ? null : converter.map(domain, getEntityClass());
    }

    protected List<E> domainListToEntities(List<D> domainList) {
        return domainList.stream().map(this::domainToEntity).collect(Collectors.toList());
    }

    @Override
    public boolean add(D dto) {
        return super.save(domainToEntity(dto));
    }

    @Override
    public boolean addAll(List<D> ds) {
        return super.saveBatch(domainListToEntities(ds));
    }

    @Override
    public List<D> list(Q q) {
        LambdaQueryWrapper<E> queryWrapper = MybatisPlusQueryHelper.buildLambdaQueryWrapper(q);
        return entitiesToDomainList(super.list(queryWrapper));
    }
}
