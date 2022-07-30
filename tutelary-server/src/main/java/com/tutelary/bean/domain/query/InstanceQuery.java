package com.tutelary.bean.domain.query;

import com.tutelary.common.bean.domain.BaseQueryDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class InstanceQuery extends BaseQueryDomain {

    private String appName;

}
