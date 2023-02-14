package com.tutelary.bean.converter;

import com.tutelary.bean.api.req.AppPageQueryRequest;
import com.tutelary.bean.api.req.AppQueryRequest;
import com.tutelary.bean.api.resp.AppInfoResponse;
import com.tutelary.bean.domain.App;
import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.common.converter.DomainResponseConverter;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.common.converter.PageQueryConverter;
import com.tutelary.common.converter.QueryConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppConverter
    extends EntityDomainConverter<AppEntity, App>, DomainResponseConverter<App, AppInfoResponse>,
    PageQueryConverter<AppPageQueryRequest, AppQuery>, QueryConverter<AppQueryRequest, AppQuery> {

}
