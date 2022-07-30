package com.tutelary.common.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> extends AbstractResponse {

    private Long total;

    private List<T> records;

}
