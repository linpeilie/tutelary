package com.tutelary.bean.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private Long total;

    private List<T> records;

}
