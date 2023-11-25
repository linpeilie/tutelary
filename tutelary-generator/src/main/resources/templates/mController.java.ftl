package ${controllerPackage};


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import ${addRequestPackage}.${addRequestClassName};
import ${editRequestPackage}.${editRequestClassName};
import ${package.Service}.${serviceClassName};
import ${pageQueryRequestPackage}.${pageQueryRequestClassName};
import ${responsePackage}.${responseName};
import ${domainClassPackage}.${domainClassName};
import ${queryDomainPackage}.${queryDomainName};
import cn.easii.tutelary.common.bean.resp.PageResult;
import cn.easii.tutelary.common.bean.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.linpeilie.Converter;
import java.util.List;
import lombok.AllArgsConstructor;

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
@RestController
// TODO:定义requestMapping
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/${domainClassName? uncap_first}")
@Tag(name = "${controllerClassName}", description = "${table.comment!}相关接口}")
@AllArgsConstructor
public class ${controllerClassName} {

    private final ${serviceClassName} ${serviceClassName? uncap_first};
    private final Converter converter;

    @PostMapping(value = "add")
    @Operation(summary = "新增", description = "新增${table.comment!}")
    @ApiResponse(description = "返回新增是否成功", content = @Content(mediaType = "application/json"))
    public R<Void> add(@RequestBody @Validated ${addRequestClassName} ${addRequestClassName? uncap_first}) {
        ${domainClassName} ${domainClassName? uncap_first} = converter.convert(${addRequestClassName? uncap_first}, ${domainClassName}.class);
        ${serviceClassName? uncap_first}.add(${domainClassName? uncap_first});
        return R.success();
    }

    @PostMapping(value = "edit")
    @Operation(summary = "编辑", description = "编辑${table.comment!}")
    @ApiResponse(description = "返回编辑是否成功", content = @Content(mediaType = "application/json"))
    public R<Void> edit(@RequestBody @Validated ${editRequestClassName} ${editRequestClassName? uncap_first}) {
        ${domainClassName} ${domainClassName? uncap_first} = converter.convert(${editRequestClassName? uncap_first}, ${domainClassName}.class);
        ${serviceClassName? uncap_first}.edit(${domainClassName? uncap_first});
        return R.success();
    }

    @PostMapping(value = "pageQuery")
    @Operation(summary = "分页查询", description = "${table.comment!}分页查询")
    @ApiResponse(description = "返回分页查询结果",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ${responseName}.class)))
    public R<PageResult<${responseName}>> pageQuery(@RequestBody @Validated ${pageQueryRequestClassName} ${pageQueryRequestClassName? uncap_first}) {
        ${queryDomainName} ${queryDomainName? uncap_first} = converter.convert(${pageQueryRequestClassName? uncap_first}, ${queryDomainName}.class);
        long count = ${serviceClassName? uncap_first}.count(${queryDomainName? uncap_first});
        if (count == 0) {
            return R.success(PageResult.empty());
        }
        List<${domainClassName}> list = ${serviceClassName? uncap_first}.list(${queryDomainName? uncap_first}, ${pageQueryRequestClassName? uncap_first}.getPageIndex(), ${pageQueryRequestClassName? uncap_first}.getPageSize());
        return R.success(new PageResult<>(count, converter.convert(list, ${responseName}.class)));
    }

}