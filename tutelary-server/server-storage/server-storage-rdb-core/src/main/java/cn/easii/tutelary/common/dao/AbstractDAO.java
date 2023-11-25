package cn.easii.tutelary.common.dao;

import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.common.repository.BaseRepository;
import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import cn.easii.tutelary.dao.common.QueryDAO;
import java.util.List;

public class AbstractDAO<P extends BaseQueryDomain, D extends BaseDomain, E extends BaseEntity> implements QueryDAO<P, D> {

    private BaseRepository<P, D, E> repository;

    public AbstractDAO(final BaseRepository<P, D, E> repository) {
        this.repository = repository;
    }

    @Override
    public boolean add(final D d) {
        return false;
    }

    @Override
    public boolean edit(D d) {
        return false;
    }

    @Override
    public boolean addAll(final List<D> ds) {
        return false;
    }

    @Override
    public List<D> list(final P param) {
        return repository.list(param);
    }

    @Override
    public List<D> list(final P param, final long pageIndex, final long pageSize) {
        return repository.list(param, pageIndex, pageSize);
    }

    @Override
    public long count(final P param) {
        return repository.count(param);
    }
}
