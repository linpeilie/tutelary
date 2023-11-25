package ${queryDomainPackage};

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
* ${table.comment!}
* </p>
*
* @author ${author}
* @since ${date}
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class ${queryDomainName} extends BaseQueryDomain {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    @Query
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->

    @Query(field = "create_time", queryType = QueryType.BETWEEN)
    private List<LocalDateTime> createTimeInterval;

}