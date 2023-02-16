package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseDomain;
import lombok.Data;

@Data
public class InstanceSimpleCommand extends BaseDomain {

    private String instanceId;

    private Integer commandCode;

    private String taskId;

    private String result;

}
