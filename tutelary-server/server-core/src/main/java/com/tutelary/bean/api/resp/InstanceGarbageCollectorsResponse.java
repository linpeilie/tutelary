package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import java.util.List;
import lombok.Data;

@Data
public class InstanceGarbageCollectorsResponse extends AbstractResponse {

    private String name;

    private List<String> memoryPoolNames;

    private List<Integer> collectionCounts;

    private List<Integer> collectionTimes;

    private List<Long> reportTimestamps;

}
