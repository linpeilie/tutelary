package cn.easii.tutelary.common.utils.matcher;

import cn.hutool.core.util.ObjectUtil;

public class EqualsMatcher<T> implements Matcher<T> {

    private final T pattern;

    public EqualsMatcher(T pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(final T target) {
        return ObjectUtil.equals(target, pattern);
    }
}
