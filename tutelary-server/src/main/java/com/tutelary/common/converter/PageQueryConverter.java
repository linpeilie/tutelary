package com.tutelary.common.converter;

import com.tutelary.common.bean.dto.BaseQueryDto;
import com.tutelary.common.bean.vo.PageRequest;

public interface PageQueryConverter<PageQueryVO extends PageRequest, PageQueryDTO extends BaseQueryDto> {

    PageQueryDTO pageQueryVoToDto(PageQueryVO pageQueryVO);

}
