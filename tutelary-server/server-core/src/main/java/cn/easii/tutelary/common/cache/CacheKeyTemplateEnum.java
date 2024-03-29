package cn.easii.tutelary.common.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeyTemplateEnum {

    WORKER("id:worker:%s"),
    LOGIN_FAULT_CNT("auth:login_fault_cnt:%s");

    private String template;

    public String toCacheKey(Object... args) {
        if (args != null && args.length > 0) {
            return String.format(this.getTemplate(), args);
        } else {
            return this.getTemplate();
        }
    }

}
