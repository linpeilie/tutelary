package com.tutelary.bean.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

}
