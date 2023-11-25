package cn.easii.tutelary.common.annotation;

import cn.easii.tutelary.common.enums.QueryType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /**
     * 对应数据库名称，如果不指定，会基于当前属性名称转下划线
     */
    String field() default "";

    QueryType queryType() default QueryType.EQ;

}
