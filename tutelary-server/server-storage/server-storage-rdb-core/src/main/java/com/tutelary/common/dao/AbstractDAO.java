package com.tutelary.common.dao;

import com.tutelary.common.domain.BaseDomain;
import com.tutelary.common.domain.BaseQueryDomain;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.common.repository.BaseRepository;
import com.tutelary.dao.common.QueryDAO;
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
    public boolean addAll(final List<D> ds) {
        return false;
    }

    @Override
    public List<D> list(final P param) {
        return null;
    }
}
