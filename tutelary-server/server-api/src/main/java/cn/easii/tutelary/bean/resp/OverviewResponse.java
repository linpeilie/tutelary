package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import java.util.List;
import lombok.Data;

@Data
public class OverviewResponse extends AbstractResponse {

    private InstanceHostResponse host;

    private InstanceThreadStatisticResponse threadStatistic;

    private List<InstanceJvmMemoryResponse> heapMemory;

    private List<InstanceJvmMemoryResponse> nonHeapMemory;

    private List<InstanceGarbageCollectorsResponse> garbageCollectors;

}
