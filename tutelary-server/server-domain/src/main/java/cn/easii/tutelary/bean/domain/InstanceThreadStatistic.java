package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseDomain;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class InstanceThreadStatistic extends BaseDomain {
    private String instanceId;

    private Integer threadCount;

    private Integer peakThreadCount;

    private Integer daemonThreadCount;

    private Integer totalStartedThreadCount;

    private LocalDateTime reportTime;
}
