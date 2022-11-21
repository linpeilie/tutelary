package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstanceJvmMemoryResponse extends AbstractResponse {

    private String type;

    private String name;

    private Integer used;

    private Integer committed;

    private Integer max;

    private LocalDateTime reportTime;

}
