package com.tutelary.bean.req;

import com.tutelary.bean.domain.CommandTaskCreate;
import com.tutelary.common.bean.req.AbstractRequest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CommandTaskCreate.class)
public class CommandCreateRequest extends AbstractRequest {
    private String instanceId;

    private Integer commandCode;

    private String param;
}
