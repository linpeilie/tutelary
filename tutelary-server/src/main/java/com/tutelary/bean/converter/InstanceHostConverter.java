package com.tutelary.bean.converter;

import com.tutelary.bean.api.resp.InstanceHostResponse;
import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.entity.InstanceHostEntity;
import com.tutelary.common.converter.DomainResponseConverter;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.message.command.domain.HostInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceHostConverter
    extends EntityDomainConverter<InstanceHostEntity, InstanceHost> {

    InstanceHost hostToInstanceHost(HostInfo hostInfo, String instanceId, LocalDateTime reportTime);

}
