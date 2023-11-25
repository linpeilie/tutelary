package cn.easii.tutelary.bean.domain.query;

import cn.easii.tutelary.common.annotation.Query;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends BaseQueryDomain {

    @Query
    private String username;

}
