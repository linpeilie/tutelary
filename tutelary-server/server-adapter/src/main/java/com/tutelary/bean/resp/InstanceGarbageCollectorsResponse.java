package com.tutelary.bean.resp;

import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.common.bean.resp.AbstractResponse;
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
