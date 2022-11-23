package com.tutelary.bean.api.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InstanceGarbageCollectorsResponse extends AbstractResponse {

    private String name;

    private List<String> memoryPoolNames;

    private List<Integer> collectionCounts;

    private List<Integer> collectionTimes;

    private List<Long> reportTimestamps;

}
