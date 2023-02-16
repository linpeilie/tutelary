package com.tutelary.common.converter;

import cn.hutool.core.util.StrUtil;
import com.tutelary.common.enums.InstanceStateEnum;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonConverter {

    default Integer map(InstanceStateEnum instanceStateEnum) {
        return instanceStateEnum.getValue();
    }

    default InstanceStateEnum map(Integer value) {
        return InstanceStateEnum.getByValue(value);
    }

    default String map(List<String> list) {
        return String.join(",", list);
    }

    default List<String> map(String str) {
        return StrUtil.split(str, ",");
    }

}
