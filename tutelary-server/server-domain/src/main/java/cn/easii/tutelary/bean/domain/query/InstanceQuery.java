package cn.easii.tutelary.bean.domain.query;

import cn.easii.tutelary.common.annotation.Query;
import cn.easii.tutelary.common.annotation.Sort;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import cn.easii.tutelary.common.enums.QueryType;
import cn.easii.tutelary.common.enums.SortDirection;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceQuery extends BaseQueryDomain {

    @Sort(direction = SortDirection.DESC)
    private Long id;

    @Query
    private String appName;

    @Query(field = "state", queryType = QueryType.IN)
    private List<Integer> states;

    @Override
    public String[] getKeywordFields() {
        return new String[] {
            "instance_id",
            "ip"
        };
    }
}
