package com.tutelary.bean.req;

import com.tutelary.bean.domain.CommandTaskCreate;
import com.tutelary.common.bean.req.AbstractRequest;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMap(targetType = CommandTaskCreate.class)
public class CommandCreateRequest extends AbstractRequest {
    private String instanceId;

    private Integer commandCode;

    private String param;
}
