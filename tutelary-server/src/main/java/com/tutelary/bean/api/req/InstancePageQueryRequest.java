package com.tutelary.bean.api.req;

import com.tutelary.common.bean.api.req.PageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstancePageQueryRequest extends PageQueryRequest {

    private String appName;

}
