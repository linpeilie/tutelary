package com.tutelary.bean.api.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InstanceThreadStatisticResponse extends AbstractResponse {

    private List<Integer> threadCount;

    private List<Integer> peakThreadCount;

    private List<Integer> daemonThreadCount;

    private Integer totalStartedThreadCount;

    private List<Long> reportTimestamps;

}
