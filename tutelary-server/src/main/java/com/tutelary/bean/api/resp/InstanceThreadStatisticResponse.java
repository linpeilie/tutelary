package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import java.util.List;
import lombok.Data;

@Data
public class InstanceThreadStatisticResponse extends AbstractResponse {

    private List<Integer> threadCount;

    private List<Integer> peakThreadCount;

    private List<Integer> daemonThreadCount;

    private Integer totalStartedThreadCount;

    private List<Long> reportTimestamps;

}
