package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.util.List;

@Data
public class OverviewResponse extends AbstractResponse {

    private InstanceHostResponse host;

    private InstanceThreadStatisticResponse threadStatistic;

    private List<InstanceJvmMemoryResponse> heapMemory;

    private List<InstanceJvmMemoryResponse> nonHeapMemory;

    private List<InstanceGarbageCollectorsResponse> garbageCollectors;

}
