package com.tutelary.bean.domain.query;

import com.tutelary.common.annotation.Query;
import com.tutelary.common.annotation.Sort;
import com.tutelary.common.bean.domain.BaseQueryDomain;

import com.tutelary.common.enums.QueryType;
import com.tutelary.common.enums.SortDirection;
import lombok.Data;

@Data
public class AppQuery extends BaseQueryDomain {

    @Sort (direction = SortDirection.DESC)
    private Long id;

    @Query(queryType = QueryType.LIKE)
    private String appName;

}
