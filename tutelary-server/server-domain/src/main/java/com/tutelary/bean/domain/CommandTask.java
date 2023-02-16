package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseDomain;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommandTask extends BaseDomain {

    private Integer commandCode;

    private String instanceId;

    private String taskId;

    private String param;

    private LocalDateTime completeTime;

}
