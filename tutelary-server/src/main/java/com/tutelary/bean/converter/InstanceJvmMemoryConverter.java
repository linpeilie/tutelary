package com.tutelary.bean.converter;

import com.tutelary.bean.api.resp.InstanceJvmMemoryResponse;
import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.entity.InstanceJvmMemoryEntity;
import com.tutelary.common.converter.DomainResponseConverter;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.message.command.domain.JvmMemory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceJvmMemoryConverter
    extends EntityDomainConverter<InstanceJvmMemoryEntity, InstanceJvmMemory> {

    InstanceJvmMemory jvmMemoryToDomain(JvmMemory jvmMemory, String instanceId, LocalDateTime reportTime, String type);

}
