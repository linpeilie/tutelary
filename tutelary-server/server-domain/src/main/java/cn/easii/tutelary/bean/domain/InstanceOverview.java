package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseDomain;
import java.util.List;
import lombok.Data;

@Data
public class InstanceOverview extends BaseDomain {

    private InstanceHost host;

    private InstanceThreadStatistic threadStatistic;

    private List<InstanceJvmMemory> jvmMemories;

    private List<InstanceGarbageCollectors> garbageCollectors;

}
