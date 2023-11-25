package cn.easii.tutelary.installer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author linpl
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    /**
     * 字段名，同 name()，优先级低
     * 如果没有配置该字段和 name()：
     *    1. 取 TableField 注解中配置的名称
     *    2. 取属性名称，驼峰转下划线，作为字段名称
     */
    String value() default "";

    /**
     * 字段名
     */
    String name() default "";

    /**
     * 类型
     */
    String dataType() default "";

    /**
     * 字段长度
     */
    int length() default 64;

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 是否是主键
     */
    boolean isKey() default false;

    /**
     * 是否自动递增
     */
    boolean isAutoIncrement() default false;

    /**
     * 默认值
     */
    String defaultValue() default "";

    /**
     * 字段备注
     */
    String comment() default "";

    /**
     * 建表时，列的顺序，由小到大
     */
    int sequence() default 1;

}
