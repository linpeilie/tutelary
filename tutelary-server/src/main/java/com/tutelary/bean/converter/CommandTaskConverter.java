package com.tutelary.bean.converter;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.entity.CommandTaskEntity;
import com.tutelary.common.converter.EntityDomainConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommandTaskConverter extends EntityDomainConverter<CommandTaskEntity, CommandTask> {

}
