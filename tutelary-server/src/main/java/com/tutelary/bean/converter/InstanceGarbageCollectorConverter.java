package com.tutelary.bean.converter;

import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.bean.entity.InstanceGarbageCollectorsEntity;
import com.tutelary.common.converter.EntityDomainConverter;
import com.tutelary.message.command.domain.GarbageCollector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceGarbageCollectorConverter
    extends EntityDomainConverter<InstanceGarbageCollectorsEntity, InstanceGarbageCollectors> {

    @Mappings({
            @Mapping(target = "memoryPoolNames",
                    expression = "java(cn.hutool.core.util.StrUtil.split(entity.getMemoryPoolNames(), \",\"))")
    })
    @Override
    InstanceGarbageCollectors entityToDomain(InstanceGarbageCollectorsEntity entity);

    @Mappings({
            @Mapping(target = "memoryPoolNames",
                    expression = "java(java.lang.String.join(\",\", dto.getMemoryPoolNames()))")
    })
    @Override
    InstanceGarbageCollectorsEntity domainToEntity(InstanceGarbageCollectors dto);

    InstanceGarbageCollectors garbageCollectorToDomain(GarbageCollector garbageCollector,
                                                       String instanceId, LocalDateTime reportTime);

}
