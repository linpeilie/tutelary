package com.tutelary.bean.converter;

import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.message.command.domain.GarbageCollector;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceGarbageCollectorConverter {

    InstanceGarbageCollectors garbageCollectorToDomain(GarbageCollector garbageCollector,
        String instanceId, LocalDateTime reportTime);

}
