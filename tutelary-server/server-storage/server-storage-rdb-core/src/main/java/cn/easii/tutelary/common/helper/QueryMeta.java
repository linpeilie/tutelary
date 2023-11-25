package cn.easii.tutelary.common.helper;

import cn.easii.tutelary.common.annotation.Query;
import cn.easii.tutelary.common.annotation.Sort;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import lombok.Data;

@Data
public class QueryMeta implements Serializable {

    private Class<?> targetClass;
    private Field field;
    private Query query;
    private Sort sort;
    private Method readMethod;

}
