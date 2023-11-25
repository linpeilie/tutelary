package cn.easii.tutelary.bean.domain.query;

import cn.easii.tutelary.common.domain.BaseQueryDomain;
import lombok.Data;

@Data
public class CommandTaskQuery extends BaseQueryDomain {

    private String instanceId;

    private String taskId;

}
