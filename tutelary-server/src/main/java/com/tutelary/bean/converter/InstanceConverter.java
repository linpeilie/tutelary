package com.tutelary.bean.converter;

import com.tutelary.bean.dto.InstanceDTO;
import com.tutelary.bean.dto.InstanceQueryDTO;
import com.tutelary.bean.entity.InstanceEntity;
import com.tutelary.bean.vo.InstancePageQueryVO;
import com.tutelary.bean.vo.InstanceVO;
import com.tutelary.common.converter.DtoVoConverter;
import com.tutelary.common.converter.EntityDtoConverter;
import com.tutelary.common.converter.PageQueryConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstanceConverter
    extends EntityDtoConverter<InstanceEntity, InstanceDTO>, DtoVoConverter<InstanceDTO, InstanceVO>,
    PageQueryConverter<InstancePageQueryVO, InstanceQueryDTO> {
}
