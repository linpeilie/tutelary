package cn.easii.tutelary.installer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author linpl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    /**
     * 表名，同 value()，优先级高
     * 如果没有配置该字段和value()，则会取 @TableName 中配置的表名
     */
    String name() default "";

    /**
     * 表名
     */
    String value() default "";

    /**
     * 注释
     */
    String comment() default "";

    /**
     * 索引
     */
    Index[] indexs() default {};

}
