package com.tutelary.common.converter;

import com.tutelary.common.bean.api.resp.PageResult;

import java.util.List;

public interface DomainResponseConverter<Domain, Response> {

    Response domainToResponse(Domain domain);

    List<Response> domainListToResponse(List<Domain> domainList);

    PageResult<Response> domainPageResultToResponse(PageResult<Domain> domainPageResult);

}
