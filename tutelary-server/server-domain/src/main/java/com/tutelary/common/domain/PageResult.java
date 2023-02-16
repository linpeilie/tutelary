package com.tutelary.common.domain;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class PageResult<T> implements Serializable {

    private Long total;

    private List<T> records;

}
