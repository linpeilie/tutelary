package cn.easii.tutelary.bean.domain.query;

import cn.easii.tutelary.common.annotation.Query;
import cn.easii.tutelary.common.annotation.Sort;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import cn.easii.tutelary.common.enums.QueryType;
import cn.easii.tutelary.common.enums.SortDirection;
import lombok.Data;

@Data
public class AppQuery extends BaseQueryDomain {

    @Sort(direction = SortDirection.DESC)
    private Long id;

    @Query(queryType = QueryType.LIKE)
    private String appName;

}
