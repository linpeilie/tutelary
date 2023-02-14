package com.tutelary.common.converter;

import com.tutelary.common.bean.api.resp.PageResult;
import java.util.List;
import java.util.stream.Collectors;

public interface DomainResponseConverter<Domain, Response> {

    Response domainToResponse(Domain domain);

    default List<Response> domainListToResponse(List<Domain> domainList) {
        return domainList.stream().map(this::domainToResponse).collect(Collectors.toList());
    }

    PageResult<Response> domainPageResultToResponse(PageResult<Domain> domainPageResult);

}
