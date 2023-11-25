package cn.easii.tutelary.bean.converter;

import cn.easii.tutelary.bean.domain.InstanceHost;
import cn.easii.tutelary.message.command.domain.HostInfo;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceHostConverter {

    InstanceHost hostToInstanceHost(HostInfo hostInfo, String instanceId, LocalDateTime reportTime);

}
