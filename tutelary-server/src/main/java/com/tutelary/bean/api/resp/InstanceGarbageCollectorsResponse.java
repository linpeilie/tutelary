package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import com.tutelary.common.bean.domain.BaseDomain;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InstanceGarbageCollectorsResponse extends AbstractResponse {

    private String name;

    private Integer collectionCount;

    private Integer collectionTime;

    private List<String> memoryPoolNames;

    private LocalDateTime reportTime;

}
