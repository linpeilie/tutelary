package com.tutelary.bean.api.req;

import com.tutelary.common.bean.api.req.PageQueryRequest;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstancePageQueryRequest extends PageQueryRequest {

    private String appName;

    private String keyword;

    private List<Integer> states;

}
