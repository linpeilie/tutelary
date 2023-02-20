package com.tutelary.bean.resp;

import com.tutelary.common.bean.resp.AbstractResponse;
import java.util.List;
import lombok.Data;

@Data
public class JvmStatisticResponse extends AbstractResponse {

    private List<InstanceJvmMemoryResponse> heapMemory;

    private List<InstanceJvmMemoryResponse> nonHeapMemory;

    private List<InstanceGarbageCollectorsResponse> garbageCollectors;

}
