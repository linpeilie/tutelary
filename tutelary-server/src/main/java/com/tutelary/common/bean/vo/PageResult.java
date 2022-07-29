package com.tutelary.common.bean.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> extends BaseResult {

    private Long total;

    private List<T> records;

}
