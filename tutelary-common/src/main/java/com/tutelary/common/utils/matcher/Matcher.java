package com.tutelary.common.utils.matcher;

/**
 * 匹配器
 */
public interface Matcher<T> {

    /**
     * 是否匹配
     *
     * @param target 目标对象
     * @return 目标对象是否匹配表达式
     */
    boolean matches(T target);

}
