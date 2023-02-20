package com.tutelary.bean.req;

import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.common.bean.req.QueryRequest;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMap(targetType = InstanceQuery.class)
public class InstanceQueryRequest extends QueryRequest {

    private String appName;

}
