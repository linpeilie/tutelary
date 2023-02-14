package com.tutelary.bean.converter;

import com.tutelary.bean.domain.InstanceSimpleCommand;
import com.tutelary.bean.entity.InstanceSimpleCommandEntity;
import com.tutelary.common.converter.EntityDomainConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SimpleCommandConverter
    extends EntityDomainConverter<InstanceSimpleCommandEntity, InstanceSimpleCommand> {
}
