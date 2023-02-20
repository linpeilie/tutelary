package com.tutelary.common.bean.req;

import lombok.Data;

@Data
public class QueryRequest extends AbstractRequest {

    private String keyword;

}
