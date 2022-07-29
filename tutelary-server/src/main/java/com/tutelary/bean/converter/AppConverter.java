package com.tutelary.bean.converter;

import com.tutelary.bean.dto.AppDTO;
import com.tutelary.bean.dto.AppQueryDTO;
import com.tutelary.bean.entity.AppEntity;
import com.tutelary.bean.vo.AppPageQueryVO;
import com.tutelary.bean.vo.AppVO;
import com.tutelary.common.converter.DtoVoConverter;
import com.tutelary.common.converter.EntityDtoConverter;
import com.tutelary.common.converter.PageQueryConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper (componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppConverter extends EntityDtoConverter<AppEntity, AppDTO>, DtoVoConverter<AppDTO, AppVO>,
    PageQueryConverter<AppPageQueryVO, AppQueryDTO> {

}
