package com.tutelary.common.extension;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Extension {

    int commandCode();

}
