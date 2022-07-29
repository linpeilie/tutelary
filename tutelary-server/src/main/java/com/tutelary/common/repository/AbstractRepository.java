package com.tutelary.common.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutelary.common.bean.dto.BaseDto;
import com.tutelary.common.bean.dto.BaseQueryDto;
import com.tutelary.common.bean.entity.BaseEntity;
import com.tutelary.common.bean.vo.PageRequest;
import com.tutelary.common.bean.vo.PageResult;
import com.tutelary.common.converter.EntityDtoConverter;
import com.tutelary.common.helper.MybatisPlusQueryHelper;

import java.util.List;

public abstract class AbstractRepository<Q extends BaseQueryDto, D extends BaseDto, E extends BaseEntity, M extends BaseMapper<E>>
    extends ServiceImpl<M, E> implements BaseRepository<Q, D, E> {

    protected EntityDtoConverter<E, D> converter;

    public AbstractRepository(EntityDtoConverter<E, D> converter) {
        this.converter = converter;
    }

    @Override
    public boolean add(D dto) {
        return super.save(converter.dtoToEntity(dto));
    }

    @Override
    public List<D> list(Q q) {
        LambdaQueryWrapper<E> queryWrapper = MybatisPlusQueryHelper.buildQueryWrapper(q);
        return converter.entitiesToDtoList(super.list(queryWrapper));
    }

    @Override
    public PageResult<D> pageList(Q q, PageRequest pageRequest) {
        LambdaQueryWrapper<E> queryWrapper = MybatisPlusQueryHelper.buildQueryWrapper(q);
        Page<E> page = Page.of(pageRequest.getPageNo(), pageRequest.getPageSize());
        return converter.entityPageToDtoPage(super.page(page, queryWrapper));
    }
}
