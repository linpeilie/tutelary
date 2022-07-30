package com.tutelary.common.bean.api.req;

import com.tutelary.common.bean.api.req.AbstractRequest;
import lombok.Data;

@Data
public class PageQueryRequest extends AbstractRequest {

    private int pageNo = 1;

    private int pageSize = 10;

}
