package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
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
