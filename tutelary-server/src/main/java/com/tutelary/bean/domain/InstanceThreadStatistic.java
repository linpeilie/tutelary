package com.tutelary.bean.domain;

import com.tutelary.common.bean.domain.BaseDomain;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstanceThreadStatistic extends BaseDomain {
    private String instanceId;

    private Integer threadCount;

    private Integer peakThreadCount;

    private Integer daemonThreadCount;

    private Integer totalStartedThreadCount;

    private LocalDateTime reportTime;
}
