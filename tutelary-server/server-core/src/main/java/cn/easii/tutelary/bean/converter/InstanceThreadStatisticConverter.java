package cn.easii.tutelary.bean.converter;

import cn.easii.tutelary.bean.domain.InstanceThreadStatistic;
import cn.easii.tutelary.message.command.domain.ThreadStatistic;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceThreadStatisticConverter {

    InstanceThreadStatistic threadStatisticToDomain(ThreadStatistic threadStatistic,
        String instanceId,
        LocalDateTime reportTime);

}
