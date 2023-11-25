package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
<#if mapperAnnotationClass??>
    import ${mapperAnnotationClass.name};
</#if>

/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if mapperAnnotationClass??>
    @${mapperAnnotationClass.simpleName}
</#if>
<#if kotlin>
    interface ${mapperClassName} : ${superMapperClass}<${entity}>
<#else>
    public interface ${mapperClassName} extends ${superMapperClass}<${entity}> {

    }
</#if>