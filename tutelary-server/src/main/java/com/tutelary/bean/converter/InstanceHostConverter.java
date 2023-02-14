package com.tutelary.bean.converter;

import com.tutelary.bean.domain.InstanceHost;
import com.tutelary.bean.entity.InstanceHostEntity;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.message.command.domain.HostInfo;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceHostConverter
    extends EntityDomainConverter<InstanceHostEntity, InstanceHost> {

    InstanceHost hostToInstanceHost(HostInfo hostInfo, String instanceId, LocalDateTime reportTime);

}
