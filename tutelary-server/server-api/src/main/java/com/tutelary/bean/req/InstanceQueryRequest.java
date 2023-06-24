package com.tutelary.bean.req;

import com.tutelary.bean.domain.query.InstanceQuery;
import com.tutelary.common.bean.req.QueryRequest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InstanceQuery.class)
public class InstanceQueryRequest extends QueryRequest {

    private String appName;

}
