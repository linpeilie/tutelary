package com.tutelary.bean.converter;

import com.tutelary.bean.domain.Instance;
import com.tutelary.message.command.domain.JvmInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceConverter {

    @Mappings({@Mapping(target = "startTime", expression = "java(java.time.LocalDateTime.ofInstant(" +
                                                           "java.time.Instant.ofEpochMilli(jvmInfo.getStartTime()), java.time.ZoneId.systemDefault()))")})
    Instance jvmInfoToInstance(JvmInfo jvmInfo, @MappingTarget Instance instance);

}
