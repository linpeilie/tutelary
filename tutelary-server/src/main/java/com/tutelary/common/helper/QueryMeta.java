package com.tutelary.common.helper;

import com.tutelary.common.annotation.Query;
import com.tutelary.common.annotation.Sort;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class QueryMeta implements Serializable {

    private Class<?> targetClass;
    private Field field;
    private Query query;
    private Sort sort;
    private Method readMethod;

}
