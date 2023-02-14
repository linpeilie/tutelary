package com.tutelary.bean.converter;

import com.tutelary.bean.api.req.InstancePageQueryRequest;
import com.tutelary.bean.api.req.InstanceQueryRequest;
import com.tutelary.bean.api.req.StatisticQueryRequest;
import com.tutelary.bean.api.resp.InstanceDetailInfoResponse;
import com.tutelary.bean.api.resp.InstanceInfoResponse;
import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.common.converter.DomainResponseConverter;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.common.converter.PageQueryConverter;
import com.tutelary.common.converter.QueryConverter;
import com.tutelary.message.command.domain.JvmInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceConverter
    extends EntityDomainConverter<InstanceEntity, Instance>, DomainResponseConverter<Instance, InstanceInfoResponse>,
    PageQueryConverter<InstancePageQueryRequest, InstanceQuery>, QueryConverter<InstanceQueryRequest, InstanceQuery> {

    @Mappings({
        @Mapping(target = "startTime", expression = "java(java.time.LocalDateTime.ofInstant(" +
                                                    "java.time.Instant.ofEpochMilli(jvmInfo.getStartTime()), java.time.ZoneId.systemDefault()))")
    })
    Instance jvmInfoToInstance(JvmInfo jvmInfo, @MappingTarget Instance instance);

    InstanceDetailInfoResponse instanceToDetailResponse(Instance instance);

    StatisticQuery overviewQueryRequestToDomain(StatisticQueryRequest request);

}
