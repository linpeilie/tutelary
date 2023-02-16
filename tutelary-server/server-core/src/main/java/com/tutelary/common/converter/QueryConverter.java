package com.tutelary.common.converter;

import com.tutelary.common.bean.api.req.QueryRequest;
import com.tutelary.common.domain.BaseQueryDomain;

public interface QueryConverter<QueryReq extends QueryRequest, QueryDomain extends BaseQueryDomain> {

    QueryDomain queryRequestToDomain(QueryReq queryReq);

}
