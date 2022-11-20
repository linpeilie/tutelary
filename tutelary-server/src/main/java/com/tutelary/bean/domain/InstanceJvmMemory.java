package com.tutelary.bean.domain;

import com.tutelary.common.bean.domain.BaseDomain;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstanceJvmMemory extends BaseDomain {

    private String instanceId;

    private String type;

    private String name;

    private Integer used;

    private Integer committed;

    private Integer max;

    private LocalDateTime reportTime;

}
