package com.tutelary.bean.req;

import com.tutelary.bean.domain.query.AppQuery;
import com.tutelary.common.bean.req.PageQueryRequest;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppQuery.class)
public class AppPageQueryRequest extends PageQueryRequest {

    private String appName;

}
