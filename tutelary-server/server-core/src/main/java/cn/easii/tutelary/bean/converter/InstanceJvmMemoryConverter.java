package cn.easii.tutelary.bean.converter;

import cn.easii.tutelary.bean.domain.InstanceJvmMemory;
import cn.easii.tutelary.message.command.domain.JvmMemory;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceJvmMemoryConverter {

    InstanceJvmMemory jvmMemoryToDomain(JvmMemory jvmMemory, String instanceId, LocalDateTime reportTime, String type);

}
