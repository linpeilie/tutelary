package com.tutelary.bean.domain.query;

import cn.hutool.core.util.ArrayUtil;
import com.tutelary.common.annotation.Query;
import com.tutelary.common.annotation.Sort;
import com.tutelary.common.bean.domain.BaseQueryDomain;
import com.tutelary.common.enums.QueryType;
import com.tutelary.common.enums.SortDirection;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode (callSuper = true)
public class InstanceQuery extends BaseQueryDomain {

    @Sort(direction = SortDirection.DESC)
    private Long id;

    @Query
    private String appName;

    @Query(field = "state", queryType = QueryType.IN)
    private List<Integer> states;

    @Override
    public String[] getKeywordFields() {
        return new String[]{"instance_id", "ip"};
    }
}
