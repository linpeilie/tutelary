package ${domainClassPackage};

import cn.easii.sparrow.core.bean.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.*;
import java.time.*;

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
public class ${domainClassName} extends BaseDomain {

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
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->

}