package cn.easii.tutelary.common.utils;

import cn.easii.tutelary.common.constants.ResponseCode;
import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.common.exception.BusinessException;
import cn.easii.tutelary.common.exception.IllegalArgumentException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.function.Supplier;

public class Asserts {

    public static <X extends BaseException> void isTrue(boolean expression, Supplier<? extends X> errorSupplier) {
        if (!expression) {
            throw errorSupplier.get();
        }
    }

    public static void isTrue(boolean expression, ResponseCode responseCode, Object... args) throws
                                                                                             IllegalArgumentException {
        isTrue(expression, () -> new BusinessException(responseCode, args));
    }

    public static void isTrue(boolean expression, String errorMessage) {
        isTrue(expression, () -> new IllegalArgumentException(errorMessage));
    }

    public static <X extends BaseException> void isFalse(boolean expression, Supplier<? extends X> errorSupplier) {
        if (expression) {
            throw errorSupplier.get();
        }
    }

    public static void isFalse(boolean expression, ResponseCode responseCode, Object... args) throws IllegalArgumentException {
        isFalse(expression, () -> new BusinessException(responseCode, args));
    }

    public static void isFalse(boolean expression, String errorMessage) {
        isFalse(expression, () -> new IllegalArgumentException(errorMessage));
    }

    public static <T, X extends BaseException> T notNull(T object, Supplier<X> errorSupplier) {
        if (null == object) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static void notEmpty(Collection<?> collection, ResponseCode responseCode, Object... args) {
        notEmpty(collection, () -> new BusinessException(responseCode, args));
    }

    public static void notEmpty(Collection<?> collection, String errorMessage) {
        notEmpty(collection, () -> new IllegalArgumentException(errorMessage));
    }

    public static <X extends BaseException> void notEmpty(Collection<?> collection, Supplier<X> errorSupplier) {
        if (CollectionUtil.isEmpty(collection)) {
            throw errorSupplier.get();
        }
    }

    public static void notEmpty(Object[] arr, ResponseCode responseCode, Object... args) {
        notEmpty(arr, () -> new BusinessException(responseCode, args));
    }

    public static void notEmpty(Object[] arr, String errorMessage) {
        notEmpty(arr, () -> new IllegalArgumentException(errorMessage));
    }

    public static <X extends BaseException> void notEmpty(Object[] arr, Supplier<X> errorSupplier) {
        if (ArrayUtil.isEmpty(arr)) {
            throw errorSupplier.get();
        }
    }

    public static <T> T notNull(T object, ResponseCode responseCode, Object... args) {
        return notNull(object, () -> new BusinessException(responseCode, args));
    }

    public static <T> T notNull(T object, String errorMessage) {
        return notNull(object, () -> new IllegalArgumentException(errorMessage));
    }

    public static <T, X extends BaseException> T isNull(T object, Supplier<X> errorSupplier) {
        if (object != null) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static <T> T isNull(T object, ResponseCode responseCode, Object... args) {
        return isNull(object, () -> new BusinessException(responseCode, args));
    }

    public static <T> T isNull(T object, String errorMessage) {
        return isNull(object, () -> new IllegalArgumentException(errorMessage));
    }

    public static <T, V, X extends BaseException> T equals(T object, V value, Supplier<X> errorSupplier) {
        if (!ObjectUtil.equals(object, value)) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static <T, V> T equals(T object, V value, ResponseCode responseCode, Object... args) {
        return equals(object, value, () -> new BusinessException(responseCode, args));
    }

    public static <T, V> T equals(T object, V value, String errorMessage) {
        return equals(object, value, () -> new IllegalArgumentException(errorMessage));
    }

    public static <T, V, X extends BaseException> T notEquals(T object, V value, Supplier<X> errorSupplier) {
        if (ObjectUtil.equals(object, value)) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static <T, V> T notEquals(T object, V value, ResponseCode responseCode, Object... args) {
        return notEquals(object, value, () -> new BusinessException(responseCode, args));
    }

    public static <T, V> T notEquals(T object, V value, String errorMessage) {
        return notEquals(object, value, () -> new IllegalArgumentException(errorMessage));
    }

    public static <X extends BaseException> String notBlank(String object, Supplier<X> errorSupplier) {
        if (StrUtil.isBlank(object)) {
            throw errorSupplier.get();
        }
        return object;
    }

    public static String notBlank(String str, ResponseCode responseCode, Object... args) {
        return notBlank(str, () -> new BusinessException(responseCode, args));
    }

    public static String notBlank(String str, String errorMessage) {
        return notBlank(str, () -> new IllegalArgumentException(errorMessage));
    }

    public static String notNull(String object, ResponseCode responseCode, Object... args) {
        return notNull(object, () -> new BusinessException(responseCode, args));
    }

}
