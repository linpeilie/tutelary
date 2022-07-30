package com.tutelary.bean.converter;

import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.bean.api.req.InstancePageQueryRequest;
import com.tutelary.bean.api.req.InstanceQueryRequest;
import com.tutelary.bean.api.resp.InstanceInfoResponse;
import com.tutelary.common.converter.DomainResponseConverter;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.common.converter.PageQueryConverter;
import com.tutelary.common.converter.QueryConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper (componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceConverter
    extends EntityDomainConverter<InstanceEntity, Instance>, DomainResponseConverter<Instance, InstanceInfoResponse>,
    PageQueryConverter<InstancePageQueryRequest, InstanceQuery>, QueryConverter<InstanceQueryRequest, InstanceQuery> {

}
