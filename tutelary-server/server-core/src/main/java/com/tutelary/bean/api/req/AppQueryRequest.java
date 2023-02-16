package com.tutelary.bean.api.req;

import com.tutelary.common.bean.api.req.QueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppQueryRequest extends QueryRequest {

    private String appName;

}
