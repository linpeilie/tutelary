package com.tutelary.common.annotation;

import com.tutelary.common.enums.QueryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target (ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)
public @interface Query {

    String field() default "";

    QueryType queryType() default QueryType.EQ;

}
