package cn.easii.tutelary.dao.common;

import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import java.util.List;

public interface QueryDAO<P extends BaseQueryDomain, D extends BaseDomain> extends Dao<D> {

    List<D> list(P param);

    List<D> list(P param, long pageIndex, long pageSize);

    long count(P param);

}
