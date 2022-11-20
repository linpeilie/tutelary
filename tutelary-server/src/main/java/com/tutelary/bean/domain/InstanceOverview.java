package com.tutelary.bean.domain;

import com.tutelary.common.bean.domain.BaseDomain;
import lombok.Data;

import java.util.List;

@Data
public class InstanceOverview extends BaseDomain {

    private InstanceHost host;

    private InstanceThreadStatistic threadStatistic;

    private List<InstanceJvmMemory> jvmMemories;

    private List<InstanceGarbageCollectors> garbageCollectors;

}
