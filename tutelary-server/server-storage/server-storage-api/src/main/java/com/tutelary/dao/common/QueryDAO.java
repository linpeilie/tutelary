package com.tutelary.dao.common;

import com.tutelary.common.domain.BaseDomain;
import com.tutelary.common.domain.BaseQueryDomain;
import java.util.List;

public interface QueryDAO<P extends BaseQueryDomain, D extends BaseDomain> extends Dao<D> {

    List<D> list(P param);

}
