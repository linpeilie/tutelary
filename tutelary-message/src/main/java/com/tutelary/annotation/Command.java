package com.tutelary.annotation;

import com.tutelary.constants.CommandEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linpl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Command {

    CommandEnum value();

}
