package cn.easii.tutelary.common.utils;

import cn.hutool.core.util.StrUtil;

public class StringUtils {

    public static String toString(Object obj) {
        return obj == null ? StrUtil.EMPTY : obj.toString();
    }

}
