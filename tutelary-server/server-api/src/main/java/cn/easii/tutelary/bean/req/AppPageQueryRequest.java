package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.PageQueryRequest;
import cn.easii.tutelary.bean.domain.query.AppQuery;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppQuery.class)
public class AppPageQueryRequest extends PageQueryRequest {

    private String appName;

}
