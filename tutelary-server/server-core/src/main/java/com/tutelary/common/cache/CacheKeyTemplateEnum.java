package com.tutelary.common.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeyTemplateEnum {

    WORKER("id:worker:%s");

    private String template;

    public String toCacheKey(Object... args) {
        if (args != null && args.length > 0) {
            return String.format(this.getTemplate(), args);
        } else {
            return this.getTemplate();
        }
    }

}
