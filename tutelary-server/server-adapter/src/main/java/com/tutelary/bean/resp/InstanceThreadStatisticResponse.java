package com.tutelary.bean.resp;

import com.tutelary.bean.domain.InstanceThreadStatistic;
import com.tutelary.common.bean.resp.AbstractResponse;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
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
