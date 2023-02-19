package com.tutelary.common.domain;

import com.tutelary.common.CommandResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseCommandDomain<R extends CommandResponse> extends BaseDomain {

    private String instanceId;

    private String taskId;

    private R result;

}
