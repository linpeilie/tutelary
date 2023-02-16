package com.tutelary.common.annotation;

import com.tutelary.common.enums.SortDirection;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sort {

    String field() default "";

    SortDirection direction() default SortDirection.ASC;

}
