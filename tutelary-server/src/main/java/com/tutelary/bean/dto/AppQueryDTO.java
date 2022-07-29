package com.tutelary.bean.dto;

import com.tutelary.common.annotation.Query;
import com.tutelary.common.annotation.Sort;
import com.tutelary.common.bean.dto.BaseDto;
import com.tutelary.common.bean.dto.BaseQueryDto;
import com.tutelary.common.bean.vo.PageRequest;

import com.tutelary.common.enums.SortDirection;
import lombok.Data;

@Data
public class AppQueryDTO extends BaseQueryDto {

    @Sort (direction = SortDirection.DESC)
    private Long id;

    @Query
    private String appName;

}
