package com.tutelary.bean.domain;

import com.tutelary.common.bean.domain.BaseDomain;
import lombok.Data;

@Data
public class InstanceSimpleCommand extends BaseDomain {

    private String instanceId;

    private String taskId;

    private String param;

    private String result;

}
