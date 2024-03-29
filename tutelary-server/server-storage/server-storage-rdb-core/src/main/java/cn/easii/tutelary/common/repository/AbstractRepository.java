package cn.easii.tutelary.common.repository;

import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.common.helper.MybatisPlusQueryHelper;
import io.github.linpeilie.Converter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository<Q extends BaseQueryDomain, D extends BaseDomain, E extends BaseEntity, M extends BaseMapper<E>>
    extends ServiceImpl<M, E> implements BaseRepository<Q, D, E> {

    protected Converter converter;

    @Autowired
    public void setConverter(final Converter converter) {
        this.converter = converter;
    }

    public AbstractRepository() {
    }

    private Class<D> getDomainClass() {
        return (Class<D>) TypeUtil.getClass(TypeUtil.getTypeArgument(getClass(), 1));
    }

    protected D entityToDomain(E entity) {
        return entity == null ? null : converter.convert(entity, getDomainClass());
    }

    protected List<D> entitiesToDomainList(List<E> entities) {
        return entities.stream().map(this::entityToDomain).collect(Collectors.toList());
    }

    protected E domainToEntity(D domain) {
        return domain == null ? null : converter.convert(domain, getEntityClass());
    }

    protected List<E> domainListToEntities(List<D> domainList) {
        return domainList.stream().map(this::domainToEntity).collect(Collectors.toList());
    }

    @Override
    public boolean add(D dto) {
        return super.save(domainToEntity(dto));
    }

    @Override
    public boolean edit(D dto) {
        return super.updateById(domainToEntity(dto));
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

    @Override
    public List<D> list(final Q q, final long pageIndex, final long pageSize) {
        final LambdaQueryWrapper<E> queryWrapper = MybatisPlusQueryHelper.buildLambdaQueryWrapper(q);
        queryWrapper.last(" limit " + (pageIndex - 1) * pageSize + "," + pageSize);
        return entitiesToDomainList(super.list(queryWrapper));
    }

    @Override
    public long count(final Q q) {
        final LambdaQueryWrapper<E> queryWrapper = MybatisPlusQueryHelper.buildLambdaQueryWrapper(q, true);
        return super.count(queryWrapper);
    }

    public D getOneForDomain(final Wrapper<E> queryWrapper) {
        return converter.convert(super.getOne(queryWrapper), getDomainClass());
    }



}
