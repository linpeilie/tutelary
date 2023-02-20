package com.tutelary.bean.req;

import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.common.bean.req.PageQueryRequest;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMap(targetType = InstanceQuery.class)
public class InstancePageQueryRequest extends PageQueryRequest {

    private String appName;

    private String keyword;

    private List<Integer> states;

}
