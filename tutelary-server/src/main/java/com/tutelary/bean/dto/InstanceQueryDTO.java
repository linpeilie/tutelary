package com.tutelary.bean.dto;

import com.tutelary.common.bean.dto.BaseQueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class InstanceQueryDTO extends BaseQueryDto {

    private String appName;

}
