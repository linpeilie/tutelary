package ${responsePackage};

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import io.github.linpeilie.annotations.AutoMapper;
import ${domainClassPackage}.${domainClassName};

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.*;
import java.time.*;

@Data
@Schema(name = "${responseName}", description = "${table.comment!}返回参数")
@AutoMapper(target = ${domainClassName}.class, convertGenerate = false)
public class ${responseName} extends AbstractResponse {

<#list table.fields as field>

    @Schema(name = "${field.propertyName}", description = "${field.comment!}")
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

}