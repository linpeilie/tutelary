package com.tutelary.common.bean.api.resp;

import java.util.List;
import lombok.Data;

@Data
public class PageResult<T> extends AbstractResponse {

    private Long total;

    private List<T> records;

}
