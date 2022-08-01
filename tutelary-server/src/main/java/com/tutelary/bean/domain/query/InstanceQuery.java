package com.tutelary.bean.domain.query;

import com.tutelary.common.annotation.Query;
import com.tutelary.common.annotation.Sort;
import com.tutelary.common.bean.domain.BaseQueryDomain;
import com.tutelary.common.enums.SortDirection;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class InstanceQuery extends BaseQueryDomain {

    @Sort(direction = SortDirection.DESC)
    private Long id;

    @Query
    private String appName;

}
