package com.tutelary.common.converter;

import com.tutelary.common.bean.domain.BaseQueryDomain;
import com.tutelary.common.bean.api.req.PageQueryRequest;

public interface PageQueryConverter<PageQueryReq extends PageQueryRequest, PageQueryDTO extends BaseQueryDomain> {

    PageQueryDTO pageQueryReqToDomain(PageQueryReq pageQueryReq);

}
