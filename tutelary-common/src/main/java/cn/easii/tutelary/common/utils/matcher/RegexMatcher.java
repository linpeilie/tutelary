package cn.easii.tutelary.common.utils.matcher;

import cn.hutool.core.util.ObjectUtil;

public class RegexMatcher implements Matcher<String> {

    private final String pattern;

    public RegexMatcher(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(final String target) {
        return !ObjectUtil.hasNull(target, pattern) && target.matches(pattern);
    }
}
