package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommandTaskCreate extends BaseDomain {

    private String token;

    private String instanceId;

    private Integer commandCode;

    private String param;

}
