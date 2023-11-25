package cn.easii.tutelary.bean.domain.query;

import cn.easii.tutelary.common.domain.BaseQueryDomain;
import cn.easii.tutelary.common.annotation.Query;
import cn.easii.tutelary.common.annotation.Sort;
import cn.easii.tutelary.common.enums.QueryType;
import java.util.List;
import java.time.*;
import java.math.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* <p>
* 角色表
* </p>
*
* @author linpeilie
* @since 2024-01-14 17:20:40
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQuery extends BaseQueryDomain {

    /**
     * 角色名称
     */
    @Query
    private String roleName;

    /**
     * 启用状态
     */
    @Query
    private Integer enableStatus;

}