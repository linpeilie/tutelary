package com.tutelary.common.converter;

import com.tutelary.common.bean.vo.PageResult;

import java.util.List;

public interface DtoVoConverter<DTO, VO> {

    VO dtoToVo(DTO dto);

    List<VO> dtoListToVoList(List<DTO> dtoList);

    PageResult<VO> dtoPageToVoPage(PageResult<DTO> dtoPage);

}
