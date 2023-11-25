package ${pageQueryRequestPackage};

import cn.easii.tutelary.common.bean.req.PageQueryRequest;
import ${queryDomainPackage}.${queryDomainName};

import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.*;
import java.time.*;
import java.math.*;

@Data
@AutoMapper(target = ${queryDomainName}.class, reverseConvertGenerate = false)
@Schema(name = "${pageQueryRequestClassName}", description = "${table.comment!}分页查询入参")
public class ${pageQueryRequestClassName} extends PageQueryRequest {

<#list table.fields as field>
    @Schema(name = "${field.propertyName}", description = "${field.comment!}")
    private ${field.propertyType} ${field.propertyName};

</#list>
<#------------  END 字段循环遍历  ---------->
}