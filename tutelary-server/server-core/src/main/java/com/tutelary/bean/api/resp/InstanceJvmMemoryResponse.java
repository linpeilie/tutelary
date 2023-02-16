package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import java.util.List;
import lombok.Data;

@Data
public class InstanceJvmMemoryResponse extends AbstractResponse {

    private String type;

    private String name;

    private List<Integer> used;

    private List<Integer> committed;

    private List<Integer> max;

    private List<Long> reportTimestamps;

}
