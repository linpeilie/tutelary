package com.tutelary.bean.domain.query;

import com.tutelary.common.bean.domain.BaseQueryDomain;
import lombok.Data;

@Data
public class CommandTaskQuery extends BaseQueryDomain {

    private String instanceId;

    private String taskId;

}
