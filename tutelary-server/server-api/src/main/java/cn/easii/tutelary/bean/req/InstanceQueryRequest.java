package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.QueryRequest;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InstanceQuery.class)
public class InstanceQueryRequest extends QueryRequest {

    private String appName;

}
