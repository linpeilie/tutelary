package com.tutelary.bean.api.req;

import com.tutelary.common.bean.api.req.AbstractRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommandApiRequest extends AbstractRequest {

    private String instanceId;

    private Integer commandCode;

    private String param;

}
