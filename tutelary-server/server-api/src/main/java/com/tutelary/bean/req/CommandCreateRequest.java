package com.tutelary.bean.req;

import com.tutelary.common.CommandRequest;
import com.tutelary.common.bean.req.AbstractRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "CommandCreateRequest", description = "创建命令入参")
public class CommandCreateRequest<P extends CommandRequest> extends AbstractRequest {

    @Schema(name = "instanceId", description = "实例ID")
    private String instanceId;

    @Schema(name = "param", description = "命令参数")
    private P param;
}
