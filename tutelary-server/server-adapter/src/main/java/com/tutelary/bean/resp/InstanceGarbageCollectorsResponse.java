package com.tutelary.bean.resp;

import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.common.bean.resp.AbstractResponse;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import java.util.List;
import lombok.Data;

@Data
@AutoMap(targetType = InstanceGarbageCollectors.class)
public class InstanceGarbageCollectorsResponse extends AbstractResponse {

    private String name;

    private List<String> memoryPoolNames;

    private List<Integer> collectionCounts;

    private List<Integer> collectionTimes;

    private List<Long> reportTimestamps;

}
