package com.tutelary.bean.converter;

import com.tutelary.bean.api.resp.InstanceDetailInfoResponse;
import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.domain.InstanceOverview;
import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.bean.api.req.InstancePageQueryRequest;
import com.tutelary.bean.api.req.InstanceQueryRequest;
import com.tutelary.bean.api.resp.InstanceInfoResponse;
import com.tutelary.common.converter.DomainResponseConverter;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.common.converter.PageQueryConverter;
import com.tutelary.common.converter.QueryConverter;
import com.tutelary.message.command.domain.JvmInfo;
import com.tutelary.message.command.result.Overview;
import org.mapstruct.*;

@Mapper (componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceConverter
    extends EntityDomainConverter<InstanceEntity, Instance>, DomainResponseConverter<Instance, InstanceInfoResponse>,
    PageQueryConverter<InstancePageQueryRequest, InstanceQuery>, QueryConverter<InstanceQueryRequest, InstanceQuery> {

    @Mappings({
            @Mapping(target = "startTime", expression = "java(java.time.LocalDateTime.ofInstant(" +
                    "java.time.Instant.ofEpochMilli(jvmInfo.getStartTime()), java.time.ZoneId.systemDefault()))")
    })
    Instance jvmInfoToInstance(JvmInfo jvmInfo, @MappingTarget Instance instance);

    InstanceDetailInfoResponse instanceToDetailResponse(Instance instance);



}
