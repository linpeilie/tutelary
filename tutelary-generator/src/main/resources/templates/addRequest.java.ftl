package ${addRequestPackage};

import cn.easii.tutelary.common.bean.req.AbstractRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import java.math.*;
import java.time.*;

import ${domainClassPackage}.${domainClassName};

@Data
@Schema(name = "${addRequestClassName}", description = "${table.comment!}新增入参}")
@AutoMapper(target = ${domainClassName}.class)
public class ${addRequestClassName} extends AbstractRequest {

<#list table.fields as field>
    <#if field.keyIdentityFlag>
    <#else>
    @Schema(name = "${field.propertyName}", description = "${field.comment!}")
    private ${field.propertyType} ${field.propertyName};
    </#if>

</#list>

}