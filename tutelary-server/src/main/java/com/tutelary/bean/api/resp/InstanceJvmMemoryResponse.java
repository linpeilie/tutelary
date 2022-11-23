package com.tutelary.bean.api.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InstanceJvmMemoryResponse extends AbstractResponse {

    private String type;

    private String name;

    private List<Integer> used;

    private List<Integer> committed;

    private List<Integer> max;

    private List<Long> reportTimestamps;

}
