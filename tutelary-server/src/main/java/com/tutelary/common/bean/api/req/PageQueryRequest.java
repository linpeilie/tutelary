package com.tutelary.common.bean.api.req;

import lombok.Data;

@Data
public class PageQueryRequest extends AbstractRequest {

    private int pageNo = 1;

    private int pageSize = 10;

}
