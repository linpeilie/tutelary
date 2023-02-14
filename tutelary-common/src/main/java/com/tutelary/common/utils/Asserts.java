package com.tutelary.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.function.Supplier;

public class Asserts {

    private static IllegalArgumentException throwIllegalException(String errorMessage) {
        return new IllegalArgumentException(errorMessage);
    }

    private static IllegalArgumentException throwIllegalException(String format, Object... args) {
        return throwIllegalException(String.format(format, args));
    }

    public static <X extends RuntimeException> void isTrue(boolean expression, Supplier<? extends X> errorSupplier) {
        if (!expression) {
            throw errorSupplier.get();
        }
    }

    public static void isTrue(boolean expression, String errorMsg) throws IllegalArgumentException {
        isTrue(expression, () -> throwIllegalException(errorMsg));
    }

    public static <X extends RuntimeException> void isFalse(boolean expression, Supplier<? extends X> errorSupplier) {
        if (expression) {
            throw errorSupplier.get();
        }
    }

    public static void isFalse(boolean expression, String errorMsg) throws IllegalArgumentException {
        isFalse(expression, () -> throwIllegalException(errorMsg));
    }

    public static <T, X extends RuntimeException> T notNull(T object, Supplier<X> errorSupplier) {
        if (null == object) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static void notEmpty(Collection<?> collection, String errorMsg) {
        notEmpty(collection, () -> throwIllegalException(errorMsg));
    }

    public static <X extends RuntimeException> void notEmpty(Collection<?> collection, Supplier<X> errorSupplier) {
        if (CollectionUtil.isEmpty(collection)) {
            throw errorSupplier.get();
        }
    }

    public static void notEmpty(Object[] objs, String errorMsg) {
        notEmpty(objs, () -> throwIllegalException(errorMsg));
    }

    public static <X extends RuntimeException> void notEmpty(Object[] objs, Supplier<X> errorSupplier) {
        if (ArrayUtil.isEmpty(objs)) {
            throw errorSupplier.get();
        }
    }

    public static <T> T notNull(T object, String errorMsg) {
        return notNull(object, () -> throwIllegalException(errorMsg));
    }

    public static <T, X extends RuntimeException> T isNull(T object, Supplier<X> errorSupplier) {
        if (object != null) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static <T> T isNull(T object, String errorMsg) {
        return isNull(object, () -> throwIllegalException(errorMsg));
    }

    public static <T, V, X extends RuntimeException> T equals(T object, V value, Supplier<X> errorSupplier) {
        if (!ObjectUtil.equals(object, value)) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static <T, V> T equals(T object, V value, String errorMsg) {
        return equals(object, value, () -> throwIllegalException(errorMsg));
    }

    public static <T, V, X extends RuntimeException> T notEquals(T object, V value, Supplier<X> errorSupplier) {
        if (ObjectUtil.equals(object, value)) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static <T, V> T notEquals(T object, V value, String errorMsg) {
        return notEquals(object, value, () -> throwIllegalException(errorMsg));
    }

    public static <X extends RuntimeException> String notBlank(String object, Supplier<X> errorSupplier) {
        if (StrUtil.isBlank(object)) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static String notBlank(String str, String errorMsg) {
        return notBlank(str, () -> throwIllegalException(errorMsg));
    }

    public static String notNull(String object, String errorMsg) {
        return notNull(object, () -> throwIllegalException(errorMsg));
    }

}
