package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseDomain;
import com.tutelary.message.command.result.ThreadList;
import lombok.Data;

@Data
public class InstanceThreadListCommand extends BaseDomain {

    private String taskId;

    private String instanceId;

    private ThreadList result;

}
