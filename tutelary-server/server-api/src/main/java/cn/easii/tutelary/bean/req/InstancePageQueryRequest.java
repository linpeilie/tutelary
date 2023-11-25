package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.PageQueryRequest;
import cn.easii.tutelary.bean.domain.query.InstanceQuery;
import io.github.linpeilie.annotations.AutoMapper;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InstanceQuery.class)
public class InstancePageQueryRequest extends PageQueryRequest {

    private String appName;

    private String keyword;

    private List<Integer> states;

}
