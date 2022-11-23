package com.tutelary.bean.converter;

import com.tutelary.bean.api.resp.InstanceThreadStatisticResponse;
import com.tutelary.bean.domain.InstanceThreadStatistic;
import com.tutelary.bean.entity.InstanceThreadStatisticEntity;
import com.tutelary.common.converter.DomainResponseConverter;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.message.command.domain.ThreadStatistic;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceThreadStatisticConverter
    extends EntityDomainConverter<InstanceThreadStatisticEntity, InstanceThreadStatistic> {

    InstanceThreadStatistic threadStatisticToDomain(ThreadStatistic threadStatistic, String instanceId, LocalDateTime reportTime);

}
