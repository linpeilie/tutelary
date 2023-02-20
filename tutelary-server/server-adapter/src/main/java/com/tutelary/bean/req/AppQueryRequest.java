package com.tutelary.bean.req;

import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.common.bean.req.QueryRequest;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMap(targetType = AppQuery.class)
public class AppQueryRequest extends QueryRequest {

    private String appName;

}
