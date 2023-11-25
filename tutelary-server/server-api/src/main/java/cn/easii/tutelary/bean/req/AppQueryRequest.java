package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.QueryRequest;
import cn.easii.tutelary.bean.domain.query.AppQuery;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppQuery.class)
public class AppQueryRequest extends QueryRequest {

    private String appName;

}
