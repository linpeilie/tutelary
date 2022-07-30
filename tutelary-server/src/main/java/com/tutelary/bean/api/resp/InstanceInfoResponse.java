package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceInfoResponse extends AbstractResponse {

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

}
