package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import cn.easii.tutelary.bean.domain.InstanceGarbageCollectors;
import io.github.linpeilie.annotations.AutoMapper;
import java.util.List;
import lombok.Data;

@Data
@AutoMapper(target = InstanceGarbageCollectors.class)
public class InstanceGarbageCollectorsResponse extends AbstractResponse {

    private String name;

    private List<String> memoryPoolNames;

    private List<Integer> collectionCounts;

    private List<Integer> collectionTimes;

    private List<Long> reportTimestamps;

}
