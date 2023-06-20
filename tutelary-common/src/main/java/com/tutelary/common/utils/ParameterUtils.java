package com.tutelary.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.tutelary.common.exception.IllegalArgumentException;
import java.util.Collection;

public class ParameterUtils {

    public static void nonNull(Object obj, String errorMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void notBlank(CharSequence str, String errorMessage) {
        if (StrUtil.isBlank(str)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void notEmpty(Collection<?> collection, String errorMessage) {
        if (CollectionUtil.isEmpty(collection)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void isTrue(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
