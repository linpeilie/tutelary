package cn.easii.tutelary.installer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引
 *
 * @author linpl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Index {

    /**
     * 索引名称，如果不传，由前缀和字段名称拼成
     */
    String indexName() default "";

    /**
     * 是否唯一
     */
    boolean unique() default false;

    /**
     * 组成索引的列
     */
    String[] columns();

}
