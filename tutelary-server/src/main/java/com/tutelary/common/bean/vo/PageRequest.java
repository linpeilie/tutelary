package com.tutelary.common.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest extends BaseRequest {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

}
