package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstanceThreadStatisticResponse extends AbstractResponse {

    private Integer threadCount;

    private Integer peakThreadCount;

    private Integer daemonThreadCount;

    private Integer totalStartedThreadCount;

    private LocalDateTime reportTime;
}
