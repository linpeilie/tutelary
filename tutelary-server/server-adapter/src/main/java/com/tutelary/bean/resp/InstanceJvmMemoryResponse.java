package com.tutelary.bean.resp;

import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.common.bean.resp.AbstractResponse;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
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
