package ${editRequestPackage};

import cn.easii.tutelary.common.bean.req.AbstractRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;
import java.math.*;
import java.time.*;
import ${domainClassPackage}.${domainClassName};

@Data
@Schema(name = "${editRequestClassName}", description = "${table.comment!}编辑入参")
@AutoMapper(target = ${domainClassName}.class, reverseConvertGenerate = false)
public class ${editRequestClassName} extends AbstractRequest {

<#list table.fields as field>
    @Schema(name = "${field.propertyName}", description = "${field.comment!}")
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->
}