package com.tutelary.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回编码定义接口
 */
public interface ResponseCode {

    int CODE_LENGTH = 4;

    String CODE_FORMAT = "%s%0" + CODE_LENGTH + "d";

    String getCode();

    String getMessage();

    int getArgs();

    Map<String, Integer> codeMap = new HashMap<>();

    /**
     * 获取指定格式的编码
     *
     * @param prefix 前缀
     * @return 前缀 + 编码（编码长度4位，不满足则补零）
     */
    default String formatCode(String prefix) {
        int initCode = 0;
        if (codeMap.containsKey(prefix)) {
            initCode = codeMap.get(prefix);
        }
        codeMap.put(prefix, ++initCode);
        return String.format(CODE_FORMAT, prefix, initCode);
    }

}
